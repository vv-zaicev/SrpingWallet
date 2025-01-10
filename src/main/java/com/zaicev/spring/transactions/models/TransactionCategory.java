package com.zaicev.spring.transactions.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="transaction_category")
public class TransactionCategory implements Comparable<TransactionCategory> {

	@Column(name = "category_name")
	private String name;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
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
