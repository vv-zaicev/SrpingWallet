package com.zaicev.spring.transactions.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.function.Predicate;

import org.springframework.format.annotation.DateTimeFormat;

public class TransactionFilter implements Predicate<Transaction> {
	private TransactionType transactionType;
	private BigDecimal minSum;
	private BigDecimal maxSum;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate minDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate maxDate;
	private TransactionCategory transactionCategory;

	@Override
	public boolean test(Transaction transaction) {
		boolean transactionTypeTest = transactionType == null || transactionType.equals(transaction.getType());
		boolean minSumTest = minSum == null || minSum.compareTo(transaction.getSum()) <= 0;
		boolean maxSumTest = maxSum == null || maxSum.compareTo(transaction.getSum()) >= 0;
		boolean minDateTest = minDate == null || minDate.compareTo(transaction.getDate()) <= 0;
		boolean maxDateTest = maxDate == null || maxDate.compareTo(transaction.getDate()) >= 0;
		boolean transactionCategoryTest = transactionCategory == null
				|| transactionCategory.equals(transaction.getCategory());

		return transactionTypeTest && minSumTest && maxSumTest && minDateTest && maxDateTest && transactionCategoryTest;
	}

	public boolean isEmpty() {
		return transactionCategory == null && transactionType == null && maxDate == null && minDate == null
				&& maxSum == null && minSum == null;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getMinSum() {
		return minSum;
	}

	public void setMinSum(BigDecimal minSum) {
		this.minSum = minSum;
	}

	public BigDecimal getMaxSum() {
		return maxSum;
	}

	public void setMaxSum(BigDecimal maxSum) {
		this.maxSum = maxSum;
	}

	public LocalDate getMinDate() {
		return minDate;
	}

	public void setMinDate(LocalDate minDate) {
		this.minDate = minDate;
	}

	public LocalDate getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(LocalDate maxDate) {
		this.maxDate = maxDate;
	}

	public TransactionCategory getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(TransactionCategory transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

}
