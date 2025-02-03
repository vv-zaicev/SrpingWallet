package com.zaicev.spring.transactions.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.config.TestDataConfig;
import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.security.models.User;
import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionType;
import com.zaicev.spring.wallet.dao.WalletDAO;
import com.zaicev.spring.wallet.models.Wallet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDataConfig.class })
public class TestTransactionDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private TransactionDAO transactionDAO;

	private Wallet wallet;

	@Before
	public void initialize() {
		transactionDAO = new TransactionDAO(sessionFactory);
		User user = new User();
		user.setUsername("name");
		user.setPassword("secret");
		UserDAO userDAO = new UserDAO(sessionFactory);
		userDAO.createUser(user);
		WalletDAO walletDAO = new WalletDAO(sessionFactory);
		wallet = new Wallet();
		wallet.setBalance(new BigDecimal(100));
		user.addWallet(wallet);
		walletDAO.createWallet(wallet);
	}

	@After
	public void tearDown() {
		sessionFactory.getCurrentSession().clear();
	}

	@Test
	@Transactional
	public void testGetAllTransaction() {

		Transaction transaction = new Transaction();
		transaction.setSum(new BigDecimal(100));
		transaction.setType(TransactionType.INCOME);
		transaction.setWallet(wallet);
		Transaction transaction2 = new Transaction();
		transaction2.setSum(new BigDecimal(10));
		transaction2.setType(TransactionType.EXPENSES);
		transaction2.setWallet(wallet);

		transactionDAO.createTransaction(transaction);
		transactionDAO.createTransaction(transaction2);
		List<Transaction> transactions = transactionDAO.getAllTransactions();

		assertThat(transactions).hasSize(2).extracting(Transaction::getType)
				.containsExactlyInAnyOrder(TransactionType.INCOME, TransactionType.EXPENSES);
	}

	@Test
	@Transactional
	public void testCreateAndGetTransaction() {
		Transaction transaction = new Transaction();
		transaction.setSum(new BigDecimal(100));
		transaction.setType(TransactionType.INCOME);
		transaction.setWallet(wallet);

		transactionDAO.createTransaction(transaction);
		Transaction receivedTransaction = transactionDAO.getTransactionById(transaction.getId());

		assertTrue(receivedTransaction != null);
		assertEquals(transaction.getSum(), receivedTransaction.getSum());
		assertEquals(transaction.getType(), receivedTransaction.getType());

	}

	@Test
	@Transactional
	public void testUpdateTransaction() {
		Transaction transaction = new Transaction();
		transaction.setSum(new BigDecimal(100));
		transaction.setType(TransactionType.INCOME);
		transaction.setWallet(wallet);
		transactionDAO.createTransaction(transaction);
		transaction.setType(TransactionType.EXPENSES);

		transactionDAO.updateTransaction(transaction);
		Transaction updatedTransaction = transactionDAO.getTransactionById(transaction.getId());

		assertTrue(updatedTransaction != null);
		assertEquals(transaction.getType(), updatedTransaction.getType());
	}

	@Test
	@Transactional
	public void testDeleteTransaction() {
		Transaction transaction = new Transaction();
		transaction.setSum(new BigDecimal(100));
		transaction.setType(TransactionType.INCOME);
		transaction.setWallet(wallet);
		transactionDAO.createTransaction(transaction);

		transactionDAO.deleteTransaction(transaction.getId());
		Transaction receivedTransaction = transactionDAO.getTransactionById(transaction.getId());

		assertTrue(receivedTransaction == null);
	}
}
