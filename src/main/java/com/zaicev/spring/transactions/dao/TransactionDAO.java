package com.zaicev.spring.transactions.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.transactions.models.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class TransactionDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public TransactionDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Transaction> getAllTransactions() {
		return sessionFactory.getCurrentSession().createQuery("from Transaction", Transaction.class).list();
	}

	@Transactional
	public Transaction getTransactionById(int id) {
		return sessionFactory.getCurrentSession().get(Transaction.class, id);
	}

	@Transactional
	public void createTransaction(Transaction newTransaction) {
		sessionFactory.getCurrentSession().persist(newTransaction);
	}

	@Transactional
	public void updateTransaction(Transaction updatedTrasnaction) {
		sessionFactory.getCurrentSession().merge(updatedTrasnaction);
	}

	@Transactional
	public void deleteTransaction(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction deletedTransaction = session.get(Transaction.class, id);
		if (deletedTransaction != null) {
			session.remove(deletedTransaction);
		}
	}

}
