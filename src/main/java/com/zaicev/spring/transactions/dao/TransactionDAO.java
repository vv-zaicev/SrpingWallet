package com.zaicev.spring.transactions.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionType;
import com.zaicev.spring.wallet.dao.WalletDAO;

@Component
public class TransactionDAO {

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertIntoUser;
	private WalletDAO walletDAO;
	private TransactionCategoryDAO transactionCategoryDAO;

	private final String SQL_SELECT_TRANSACTIONS = "SELECT * FROM transactions";
	private final String SQL_SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactions WHERE transaction_id=?";
	private final String SQL_UPDATE_TRANSACTION = "UPDATE transactions SET transaction_description=?, transaction_type=?, transaction_date=?, transaction_sum=?, transaction_category=?, transaction_wallet=? WHERE transaction_id=?";
	private final String SQL_DELETE_TRANSACTION = "DELETE FROM transactions WHERE transaction_id=?";
	private final String SQL_DELETE_TRANSACTIONS_BY_WALLET_ID = "DELETE FROM transactions WHERE transaction_wallet=?";

	private final RowMapper<Transaction> transactionMapper = (rs, rowNum) -> {
		Transaction transaction = new Transaction();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(rs.getDate("transaction_date"));

		transaction.setId(rs.getInt("transaction_id"));
		transaction.setSum(rs.getBigDecimal("transaction_sum"));
		transaction.setType(TransactionType.valueOf(rs.getString("transaction_type")));
		transaction.setDate(calendar);
		transaction.setDescription(rs.getString("transaction_description"));

		transaction.setCategory(transactionCategoryDAO.getCategoryById(rs.getInt("transaction_category")));
		transaction.setWallet(walletDAO.getWalletById(rs.getInt("transaction_wallet")));

		return transaction;
	};

	@Autowired
	public TransactionDAO(JdbcTemplate jdbcTemplate, WalletDAO walletDAO,
			TransactionCategoryDAO transactionCategoryDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.walletDAO = walletDAO;
		this.transactionCategoryDAO = transactionCategoryDAO;
		this.insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("transactions")
				.usingGeneratedKeyColumns("transaction_id");
	}

	public List<Transaction> getAllTransactions(int walletId) {
		return jdbcTemplate.query(SQL_SELECT_TRANSACTIONS, transactionMapper);
	}

	public Transaction getTransactionById(int id) {
		return jdbcTemplate.queryForObject(SQL_SELECT_TRANSACTION_BY_ID, transactionMapper, id);
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
		jdbcTemplate.update(SQL_UPDATE_TRANSACTION, updatedTrasnaction.getDescription(),
				updatedTrasnaction.getType().toString(), updatedTrasnaction.getDate(), updatedTrasnaction.getSum(),
				updatedTrasnaction.getCategory().getId(), updatedTrasnaction.getWallet().getId(),
				updatedTrasnaction.getId());
	}

	public void deleteTransaction(int id) {
		jdbcTemplate.update(SQL_DELETE_TRANSACTION, id);
	}

	public void deleteTransactionsByWalletId(int id) {
		jdbcTemplate.update(SQL_DELETE_TRANSACTIONS_BY_WALLET_ID, id);
	}

}
