package com.zaicev.spring.transactions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zaicev.spring.transactions.models.TransactionCategory;

public class TransactionCategoryDAO {
	private SessionFactory sessionFactory;

	public TransactionCategoryDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<TransactionCategory> getAllCategories() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from TransactionCategory", TransactionCategory.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TransactionCategory>();
		}
	}

	public TransactionCategory getCategoryById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(TransactionCategory.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createCategory(TransactionCategory newCategory) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.persist(newCategory);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCategory(TransactionCategory updatedCategory) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.merge(updatedCategory);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCategory(int id) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			TransactionCategory transactionCategory = session.get(TransactionCategory.class, id);
			if (transactionCategory != null) {
				session.remove(transactionCategory);
				;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
