package com.zaicev.spring.transactions.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TransactionFilterTest {
	private TransactionFilter filter;
	private Transaction transaction;

	@Before
	public void initialize() {
		filter = new TransactionFilter();
		transaction = new Transaction(new BigDecimal(100), TransactionType.EXPENSES, LocalDate.of(2024, 1, 1), 1,
				new TransactionCategory("FOOD", 1));
	}

	@Test
	public void allFiltersNull() {
		assertTrue(filter.test(transaction));
	}

	@Test
	public void transactionTypeMatches() {
		filter.setTransactionType(TransactionType.EXPENSES);
		assertTrue(filter.test(transaction));
	}

	@Test
	public void transactionTypeDoesNotMatch() {
		filter.setTransactionType(TransactionType.INCOME);
		assertFalse(filter.test(transaction));
	}

	@Test
	public void minSumMatches() {
		filter.setMinSum(new BigDecimal("50.00"));
		assertTrue(filter.test(transaction));
	}

	@Test
	public void minSumGreaterThanTransaction() {
		filter.setMinSum(new BigDecimal("150.00"));
		assertFalse(filter.test(transaction));
	}

	@Test
	public void maxSumMatches() {
		filter.setMaxSum(new BigDecimal("200.00"));
		assertTrue(filter.test(transaction));
	}

	@Test
	public void maxSumLessThanTransaction() {
		filter.setMaxSum(new BigDecimal("50.00"));
		assertFalse(filter.test(transaction));
	}

	@Test
	public void minDateMatches() {
		filter.setMinDate(LocalDate.of(2024, 1, 1));
		assertTrue(filter.test(transaction));
	}

	@Test
	public void minDateGreaterThanTransaction() {
		filter.setMinDate(LocalDate.of(2024, 2, 1));
		assertFalse(filter.test(transaction));
	}

	@Test
	public void maxDateMatches() {
		filter.setMaxDate(LocalDate.of(2024, 12, 31));
		assertTrue(filter.test(transaction));
	}

	@Test
	public void maxDateLessThanTransaction() {
		filter.setMaxDate(LocalDate.of(2023, 12, 31));
		assertFalse(filter.test(transaction));
	}

	@Test
	public void transactionCategoryMatches() {
		filter.setTransactionCategory(new TransactionCategory("FOOD", 1));
		assertTrue(filter.test(transaction));
	}

	@Test
	public void transactionCategoryDoesNotMatch() {
		filter.setTransactionCategory(new TransactionCategory("SOMETHING", 2));
		assertFalse(filter.test(transaction));
	}

	@Test
	public void allFiltersSetAndMatch() {
		filter.setTransactionType(TransactionType.EXPENSES);
		filter.setMinSum(new BigDecimal("50.00"));
		filter.setMaxSum(new BigDecimal("200.00"));
		filter.setMinDate(LocalDate.of(2023, 1, 1));
		filter.setMaxDate(LocalDate.of(2025, 1, 1));
		filter.setTransactionCategory(new TransactionCategory("FOOD", 1));

		assertTrue(filter.test(transaction));
	}

	@Test
	public void allFiltersSetAndOneDoesNotMatch() {
		filter.setTransactionType(TransactionType.EXPENSES);
		filter.setMinSum(new BigDecimal("50.00"));
		filter.setMaxSum(new BigDecimal("200.00"));
		filter.setMinDate(LocalDate.of(2023, 1, 1));
		filter.setMaxDate(LocalDate.of(2025, 1, 1));
		filter.setTransactionCategory(new TransactionCategory("SOMETHING", 2));

		assertFalse(filter.test(transaction));
	}

	@Test
	public void testIsEmpty_AllFieldsNull_ReturnsTrue() {
		assertTrue(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_TransactionCategoryNotNull() {
		filter.setTransactionCategory(new TransactionCategory());
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_TransactionTypeNotNull() {
		filter.setTransactionType(TransactionType.INCOME);
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_MaxDateNotNull() {
		filter.setMaxDate(LocalDate.now());
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_MinDateNotNull() {
		filter.setMinDate(LocalDate.now());
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_MaxSumNotNull() {
		filter.setMaxSum(new BigDecimal(0));
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_MinSumNotNull() {
		filter.setMinSum(new BigDecimal(0));
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_MultipleFieldsNotNull() {
		filter.setTransactionType(TransactionType.EXPENSES);
		filter.setMinDate(LocalDate.now());
		filter.setMaxSum(new BigDecimal(0));
		assertFalse(filter.isEmpty());
	}

	@Test
	public void testIsEmpty_AllFieldsNotNull() {
		filter.setTransactionCategory(new TransactionCategory());
		filter.setTransactionType(TransactionType.EXPENSES);
		filter.setMaxDate(LocalDate.now());
		filter.setMinDate(LocalDate.now());
		filter.setMaxSum(new BigDecimal(0));
		filter.setMinSum(new BigDecimal(0));
		assertFalse(filter.isEmpty());
	}

}
