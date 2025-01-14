package com.zaicev.spring.transactions.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.transactions.models.TransactionCategory;

public class TransactionCategoryDAO {
	private SessionFactory sessionFactory;

	public TransactionCategoryDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<TransactionCategory> getAllCategories() {
		return sessionFactory.getCurrentSession().createQuery("from TransactionCategory", TransactionCategory.class).list();

	}

	@Transactional
	public TransactionCategory getCategoryById(int id) {
		return sessionFactory.getCurrentSession().get(TransactionCategory.class, id);
	}

	@Transactional
	public void createCategory(TransactionCategory newCategory) {
		sessionFactory.getCurrentSession().persist(newCategory);

	}

	@Transactional
	public void updateCategory(TransactionCategory updatedCategory) {
		sessionFactory.getCurrentSession().merge(updatedCategory);
	}

	@Transactional
	public void deleteCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		TransactionCategory transactionCategory = session.get(TransactionCategory.class, id);
		if (transactionCategory != null) {
			session.remove(transactionCategory);
		}
	}
}
