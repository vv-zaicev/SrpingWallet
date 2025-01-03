package com.zaicev.spring.transactions.models;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.Predicate;

public class TransactionFilter implements Predicate<Transaction> {
	private Optional<TransactionType> transactionType;
	private Optional<BigDecimal> minSum;
	private Optional<BigDecimal> maxSum;
	private Optional<Calendar> minDate;
	private Optional<Calendar> maxDate;
	private Optional<TransactionCategory> transactionCategory;

	@Override
	public boolean test(Transaction transaction) {
		boolean transactionTypeTest = transactionType.isEmpty() || transactionType.get().equals(transaction.getType());
		boolean minSumTest = minSum.isEmpty() || minSum.get().compareTo(transaction.getSum()) <= 0;
		boolean maxSumTest = maxSum.isEmpty() || maxSum.get().compareTo(transaction.getSum()) >= 0;
		boolean minDateTest = minDate.isEmpty() || minDate.get().compareTo(transaction.getDate()) <= 0;
		boolean maxDateTest = maxDate.isEmpty() || maxDate.get().compareTo(transaction.getDate()) >= 0;
		boolean transactionCategoryTest = transactionCategory.isEmpty()
				|| transactionCategory.get().equals(transaction.getCategory());

		return transactionTypeTest && minSumTest && maxSumTest && minDateTest && maxDateTest && transactionCategoryTest;
	}

	public Optional<TransactionType> getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = Optional.ofNullable(transactionType);
	}

	public Optional<BigDecimal> getMinSum() {
		return minSum;
	}

	public void setMinSum(BigDecimal minSum) {
		this.minSum = Optional.ofNullable(minSum);
	}

	public Optional<BigDecimal> getMaxSum() {
		return maxSum;
	}

	public void setMaxSum(BigDecimal maxSum) {
		this.maxSum = Optional.ofNullable(maxSum);
	}

	public Optional<Calendar> getMinDate() {
		return minDate;
	}

	public void setMinDate(Calendar minDate) {
		this.minDate = Optional.ofNullable(minDate);
	}

	public Optional<Calendar> getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Calendar maxDate) {
		this.maxDate = Optional.ofNullable(maxDate);
	}

	public Optional<TransactionCategory> getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(Optional<TransactionCategory> transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

}
