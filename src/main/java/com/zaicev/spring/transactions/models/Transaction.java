package com.zaicev.spring.transactions.models;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.zaicev.spring.wallet.models.Wallet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction implements Comparable<Transaction> {

	@Column(name = "description")
	private String description;

	@Column(name = "transaction_sum")
	private BigDecimal sum;

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType type;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "transaction_date")
	private Calendar date;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Wallet wallet;

	@ManyToOne(fetch = FetchType.EAGER)
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

	public Calendar getDate() {
		return date;
	}

	public String getStringDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date.getTime());
	}

	public void setDate(Calendar date) {
		this.date = date;
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

	@Override
	public int compareTo(Transaction o) {
		return o.getDate().compareTo(this.getDate());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transaction)) {
			return false;
		}
		return ((Transaction) obj).getId() == this.getId();
	}

}