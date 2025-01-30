package com.zaicev.spring.transactions.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.zaicev.spring.wallet.models.Wallet;

public class TransactionTest {
	private Wallet wallet;
	private Transaction incomeTransaction;
	private Transaction expenseTransaction;

	@Before
	public void initialize() {
		wallet = new Wallet(new BigDecimal(1000), "someWallet", 1);
		incomeTransaction = new Transaction(new BigDecimal(100), TransactionType.INCOME, LocalDate.of(2024, 1, 1), 1,
				new TransactionCategory());
		expenseTransaction = new Transaction(new BigDecimal(100), TransactionType.EXPENSES,
				LocalDate.of(2024, 1, 1), 1, new TransactionCategory());
		incomeTransaction.setWallet(wallet);
		expenseTransaction.setWallet(wallet);
	}

	@Test
	public void setSum_WithIncomeTransaction() {
		incomeTransaction.setSum(new BigDecimal(300));

		assertEquals(new BigDecimal(1200), wallet.getBalance());
		assertEquals(new BigDecimal(300), incomeTransaction.getSum());
	}

	@Test
	public void setSum_WithExpensesTransaction() {
		expenseTransaction.setSum(new BigDecimal(50));

		assertEquals(new BigDecimal(1050), wallet.getBalance());
		assertEquals(new BigDecimal(50), expenseTransaction.getSum());
	}

	@Test
	public void setSum_WithoutWallet() {
		incomeTransaction.setWallet(null);
		incomeTransaction.setSum(new BigDecimal(300));

		assertNull(incomeTransaction.getWallet());
		assertEquals(new BigDecimal(300), incomeTransaction.getSum());
	}

	@Test
	public void setType_IncomeToExpenses() {
		incomeTransaction.setType(TransactionType.EXPENSES);
		
		assertEquals(TransactionType.EXPENSES, incomeTransaction.getType());
		assertEquals(new BigDecimal(800), wallet.getBalance());
	}
	
	@Test
	public void setType_ExpensesToIncome() {
		expenseTransaction.setType(TransactionType.INCOME);
		
		assertEquals(TransactionType.INCOME, expenseTransaction.getType());
		assertEquals(new BigDecimal(1200), wallet.getBalance());
	}
	
	@Test
	public void getStringDate() {
		assertEquals("01.01.2024", incomeTransaction.getStringDate());
	}

}
