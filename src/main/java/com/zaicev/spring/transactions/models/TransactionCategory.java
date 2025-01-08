package com.zaicev.spring.transactions.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionCategory implements Comparable<TransactionCategory> {

	private String name;
	@Id
	private int id;

	public TransactionCategory() {

	}

	public TransactionCategory(String name, int id) {
		this.name = name;
		this.id = id;
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

	@Override
	public int compareTo(TransactionCategory transactionCategory) {
		return this.id - transactionCategory.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TransactionCategory)) {
			return false;
		}
		return ((TransactionCategory) obj).getId() == this.getId();
	}

}
