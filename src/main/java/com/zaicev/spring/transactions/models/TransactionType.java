package com.zaicev.spring.transactions.models;

public enum TransactionType {
	INCOME("Income"), EXPENSES("Expenses"), ALL("%");

	private String SQLNameString;

	private TransactionType(String SQLNameString) {
		this.SQLNameString = SQLNameString;
	}

	public String getSQLNameString() {
		return SQLNameString;
	}
}
