package com.zaicev.spring.transactions.models;

import java.util.ArrayList;
import java.util.List;

import com.zaicev.spring.models.VisibilityType;
import com.zaicev.spring.security.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_category")
public class TransactionCategory implements Comparable<TransactionCategory> {

	@Column(name = "category_name")
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "category")
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@Enumerated(EnumType.STRING)
	@Column(name = "visibility")
	private VisibilityType visibilityType;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

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

	public VisibilityType getVisibilityType() {
		return visibilityType;
	}

	public void setVisibilityType(VisibilityType visibilityType) {
		this.visibilityType = visibilityType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PreRemove
	private void preRemove() {
		for (Transaction transaction : transactions) {
			transaction.setCategory(null);
		}
		transactions.clear();
		user.removeTransactionCategory(this);
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
