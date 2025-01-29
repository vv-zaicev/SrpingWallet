package com.zaicev.spring.wallet.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionCategory;
import com.zaicev.spring.transactions.models.TransactionType;

public class WalletTest {
	private Wallet emptyWallet;
	private Wallet fulledWallet;

	@Before
	public void initialize() {
		emptyWallet = new Wallet(new BigDecimal(0), "emptyWallet", 1);
		fulledWallet = new Wallet(new BigDecimal(0), "fulledWallet", 2);
		fulledWallet.addTransaction(new Transaction(new BigDecimal(100), TransactionType.INCOME,
				LocalDate.of(2024, 10, 10), 1, new TransactionCategory("catIn", 0)));
		fulledWallet.addTransaction(new Transaction(new BigDecimal(250), TransactionType.INCOME,
				LocalDate.of(2024, 11, 11), 2, new TransactionCategory("catIn", 0)));
		fulledWallet.addTransaction(new Transaction(new BigDecimal(150), TransactionType.EXPENSES,
				LocalDate.of(2024, 12, 12), 3, new TransactionCategory("catEx", 1)));
	}

	@Test
	public void getIncome_WhenFulledWallet() {
		Assert.assertEquals(new BigDecimal(350), fulledWallet.getIncome());

	}

	@Test
	public void getIncome_WhenEmptyWallet() {
		Assert.assertEquals(new BigDecimal(0), emptyWallet.getIncome());
	}

	@Test
	public void getExpenses_WhenFulledWallet() {
		Assert.assertEquals(new BigDecimal(150), fulledWallet.getExpenses());

	}

	@Test
	public void getExpenses_WhenEmptyWallet() {
		Assert.assertEquals(new BigDecimal(0), emptyWallet.getIncome());
	}

	@Test
	public void addTransaction_WhenTypeIncome() {
		Transaction transaction = new Transaction(new BigDecimal(100), TransactionType.INCOME,
				LocalDate.of(2024, 10, 10), 1, new TransactionCategory("catIn", 0));
		emptyWallet.addTransaction(transaction);

		Assert.assertEquals(new BigDecimal(100), emptyWallet.getBalance());
		Assert.assertEquals(emptyWallet, transaction.getWallet());
		Assert.assertTrue(emptyWallet.getTransactions().contains(transaction));
	}

	@Test
	public void addTransaction_WhenTypeExpenses() {
		Transaction transaction = new Transaction(new BigDecimal(100), TransactionType.EXPENSES,
				LocalDate.of(2024, 10, 10), 1, new TransactionCategory("catIn", 0));
		emptyWallet.addTransaction(transaction);

		Assert.assertEquals(new BigDecimal(-100), emptyWallet.getBalance());
		Assert.assertEquals(emptyWallet, transaction.getWallet());
		Assert.assertTrue(emptyWallet.getTransactions().contains(transaction));
	}

	@Test
	public void removeTransaction_WhenTypeIncome() {
		Transaction transaction = new Transaction(new BigDecimal(100), TransactionType.INCOME,
				LocalDate.of(2024, 10, 10), 1, new TransactionCategory("catIn", 0));

		fulledWallet.removeTransaction(transaction);

		Assert.assertEquals(new BigDecimal(100), fulledWallet.getBalance());
		Assert.assertNull(transaction.getWallet());
		Assert.assertFalse(fulledWallet.getTransactions().contains(transaction));
	}

	@Test
	public void removeTransaction_WhenTypeExpenses() {
		Transaction transaction = new Transaction(new BigDecimal(150), TransactionType.EXPENSES,
				LocalDate.of(2024, 12, 12), 3, new TransactionCategory("catEx", 1));

		fulledWallet.removeTransaction(transaction);

		Assert.assertEquals(new BigDecimal(350), fulledWallet.getBalance());
		Assert.assertNull(transaction.getWallet());
		Assert.assertFalse(fulledWallet.getTransactions().contains(transaction));
	}

}
