package com.zaicev.spring.wallet.models;

import java.math.BigDecimal;
import java.util.List;

import com.zaicev.spring.transactions.models.Transaction;

public class Wallet {
	private List<Transaction> transactions;
    private BigDecimal balance;
    private BigDecimal income;
    private BigDecimal expenses;
    private String name;
    private int id;
    

    
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getExpenses() {
		return expenses;
	}
	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
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
