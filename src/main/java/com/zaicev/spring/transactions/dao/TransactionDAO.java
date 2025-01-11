package com.zaicev.spring.transactions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public List<Transaction> getAllTransactions() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Transaction", Transaction.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Transaction>();
		}
	}

	public List<Transaction> getTransactionsByWalletId(int walletId) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);

			Root<Transaction> root = criteriaQuery.from(Transaction.class);
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), walletId));

			return session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Transaction>();
		}
	}

	public Transaction getTransactionById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Transaction.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createTransaction(Transaction newTransaction) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.persist(newTransaction);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateTransaction(Transaction updatedTrasnaction) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.merge(updatedTrasnaction);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTransaction(int id) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			Transaction deletedTransaction = session.get(Transaction.class, id);
			if (deletedTransaction != null) {
				session.remove(deletedTransaction);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
