package com.zaicev.spring.wallet.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.zaicev.spring.security.models.User;
import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.transactions.models.TransactionFilter;
import com.zaicev.spring.transactions.models.TransactionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Wallet {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@Column(name = "wallet_balance")
	private BigDecimal balance;

	@Column(name = "wallet_name")
	private String name;

	@Transient
	private TransactionFilter filter = new TransactionFilter();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public TransactionFilter getFilter() {
		return filter;
	}

	public void setFilter(TransactionFilter filter) {
		this.filter = filter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		List<Transaction> copyTransactions = transactions.stream().filter(filter).sorted().toList();
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
		transaction.setWallet(this);
		changeBalance(transaction, x -> x == TransactionType.INCOME);
	}

	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
		transaction.setWallet(null);
		changeBalance(transaction, x -> x == TransactionType.EXPENSES);
	}

	public void updateTransaction(Transaction updatedTransaction) {
		Transaction oldTransaction = transactions.stream().filter(x -> x.getId() == updatedTransaction.getId())
				.findFirst().orElse(null);
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

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Wallet)) {
			return false;
		}
		return id == ((Wallet) obj).getId();
	}

}
