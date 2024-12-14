package com.zaicev.spring.transactions.models;

import java.math.BigDecimal;
import java.util.Calendar;

import com.zaicev.spring.wallet.models.Wallet;

public class Transaction {
    private String description;
    private BigDecimal sum;
    private TransactionType type;
    private Calendar calendar;
    private int id;
    private Wallet wallet;
    private TransactionCategory category;
    
	public Transaction() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}
    
	
    
}