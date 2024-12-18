package com.zaicev.spring.wallet.models;

import java.math.BigDecimal;
import java.util.List;

import com.zaicev.spring.transactions.models.Transaction;

public class Wallet {
	private List<Transaction> transactions;
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
		return null;
	}

	public BigDecimal getExpenses() {
		return null;
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
		return transactions;
	}

}
