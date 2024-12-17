package com.zaicev.spring.transactions.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionMapper;

@Component
public class TransactionDAO {

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertIntoUser;
	private final String SQL_SELECT_TRANSACTIONS = "SELECT * FROM transactions LEFT JOIN transaction_category ON transaction_category = category_id";
	private final String SQL_SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactions LEFT JOIN transaction_category ON transaction_category = category_id WHERE transaction_id=?";
	private final String SQL_UPDATE_TRANSACTION = "UPDATE transactions SET transaction_description=?, transaction_type=?, transaction_date=?, transaction_sum=?, transaction_category=?, transaction_wallet=? WHERE transaction_id=?";
	private final String SQL_DELETE_TRANSACTION = "DELETE FROM transactions WHERE transaction_id=?";

	@Autowired
	public TransactionDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("transactions")
				.usingGeneratedKeyColumns("transaction_id");
	}

	public List<Transaction> getAllTransactions(int walletId) {
		return jdbcTemplate.query(SQL_SELECT_TRANSACTIONS, new TransactionMapper());
	}

	public Transaction getTransactionById(int id) {
		return jdbcTemplate.queryForObject(SQL_SELECT_TRANSACTION_BY_ID, new TransactionMapper(), id);
	}

	public int createTransaction(Transaction newTransaction) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("transaction_description", newTransaction.getDescription());
		parameters.put("transaction_type", newTransaction.getType());
		parameters.put("transaction_date", newTransaction.getDate());
		parameters.put("transaction_sum", newTransaction.getSum());
		parameters.put("transaction_category", newTransaction.getCategory().getId());
		parameters.put("transaction_wallet", newTransaction.getWallet().getId());

		return insertIntoUser.executeAndReturnKey(parameters).intValue();

	}

	public void updateTransaction(Transaction updatedTrasnaction) {
		jdbcTemplate.update(SQL_UPDATE_TRANSACTION, updatedTrasnaction.getDescription(), updatedTrasnaction.getType().toString(),
				updatedTrasnaction.getDate(), updatedTrasnaction.getSum(), updatedTrasnaction.getCategory().getId(),
				updatedTrasnaction.getWallet().getId(), updatedTrasnaction.getId());
	}

	public void deleteTransaction(int id) {
		jdbcTemplate.update(SQL_DELETE_TRANSACTION, id);
	}

}
