package com.zaicev.spring.wallet.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionType;

public class Wallet {
	private List<Transaction> transactions = new ArrayList<Transaction>();
	private BigDecimal balance;
	private String name;
	private int id;

	public Wallet() {
	}

	public Wallet(BigDecimal balance, String name, int id) {
		super();
		this.balance = balance;
		this.name = name;
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getIncome() {
		return transactions.stream().filter(x -> x.getType() == TransactionType.INCOME).map(Transaction::getSum)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal getExpenses() {
		return transactions.stream().filter(x -> x.getType() == TransactionType.EXPENSES).map(Transaction::getSum)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Transaction> getTransactions() {
		List<Transaction> copyTransactions = transactions.stream().sorted().toList();
		return copyTransactions;
	}

	public void addTransactions(List<Transaction> transactions) {
		this.transactions.addAll(transactions);
	}

	public void clearTranactions() {
		transactions.clear();
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
		changeBalance(transaction, x -> x == TransactionType.INCOME);
	}

	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
		changeBalance(transaction, x -> x == TransactionType.EXPENSES);
	}

	public void updateTransaction(Transaction updatedTransaction) {
		Transaction oldTransaction = transactions.stream().filter(x -> x.getId() == updatedTransaction.getId()).findFirst().orElse(null);
		removeTransaction(oldTransaction);
		addTransaction(updatedTransaction);
	}

	private void changeBalance(Transaction transaction, Predicate<TransactionType> predicate) {
		if (predicate.test(transaction.getType())) {
			balance = balance.add(transaction.getSum());
		} else {
			balance = balance.subtract(transaction.getSum());
		}
	}

}
