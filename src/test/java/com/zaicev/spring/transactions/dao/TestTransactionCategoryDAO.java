package com.zaicev.spring.transactions.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.config.TestDataConfig;
import com.zaicev.spring.models.VisibilityType;
import com.zaicev.spring.transactions.models.TransactionCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDataConfig.class })
public class TestTransactionCategoryDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private TransactionCategoryDAO transactionCategoryDAO;

	@Before
	public void initialize() {
		transactionCategoryDAO = new TransactionCategoryDAO(sessionFactory);
	}

	@After
	public void tearDown() {
		sessionFactory.getCurrentSession().clear();
	}

	@Test
	@Transactional
	public void testCreateAndGetByIdTransactionCategory() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setName("category");

		transactionCategoryDAO.createCategory(transactionCategory);
		TransactionCategory recievedTransactionCategory = transactionCategoryDAO
				.getCategoryById(transactionCategory.getId());

		assertTrue(recievedTransactionCategory != null);
		assertEquals(transactionCategory.getId(), recievedTransactionCategory.getId());
		assertEquals(transactionCategory.getName(), recievedTransactionCategory.getName());
	}

	@Test
	@Transactional
	public void testUpdateUser() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setName("category");
		transactionCategoryDAO.createCategory(transactionCategory);
		transactionCategory.setName("UpdatedCategory");

		transactionCategoryDAO.updateCategory(transactionCategory);
		TransactionCategory updatedTransactionCategory = transactionCategoryDAO
				.getCategoryById(transactionCategory.getId());

		assertTrue(updatedTransactionCategory != null);
		assertEquals(transactionCategory.getName(), updatedTransactionCategory.getName());
	}

	@Test
	@Transactional
	public void testDeleteCategory() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setName("category");

		transactionCategoryDAO.createCategory(transactionCategory);
		transactionCategoryDAO.deleteCategory(transactionCategory.getId());
		TransactionCategory recievedTransactionCategory = transactionCategoryDAO
				.getCategoryById(transactionCategory.getId());

		assertTrue(recievedTransactionCategory == null);
	}

	@Test
	@Transactional
	public void testGetAllCategories() {
		TransactionCategory transactionCategory1 = new TransactionCategory();
		transactionCategory1.setName("food");
		TransactionCategory transactionCategory2 = new TransactionCategory();
		transactionCategory2.setName("transport");
		transactionCategoryDAO.createCategory(transactionCategory1);
		transactionCategoryDAO.createCategory(transactionCategory2);

		List<TransactionCategory> transactionCategories = transactionCategoryDAO.getAllCategories();

		assertThat(transactionCategories).hasSize(2).extracting(TransactionCategory::getName)
				.containsExactlyInAnyOrder("food", "transport");

	}

	@Test
	@Transactional
	public void testGetCategoriesByVisibleType() {
		TransactionCategory transactionCategory1 = new TransactionCategory();
		transactionCategory1.setName("food");
		transactionCategory1.setVisibilityType(VisibilityType.ALL);
		TransactionCategory transactionCategory2 = new TransactionCategory();
		transactionCategory2.setName("transport");
		transactionCategory2.setVisibilityType(VisibilityType.PERSONAL);
		TransactionCategory transactionCategory3 = new TransactionCategory();
		transactionCategory3.setName("some");
		transactionCategory3.setVisibilityType(VisibilityType.ALL);
		transactionCategoryDAO.createCategory(transactionCategory1);
		transactionCategoryDAO.createCategory(transactionCategory2);
		transactionCategoryDAO.createCategory(transactionCategory3);

		List<TransactionCategory> transactionCategories = transactionCategoryDAO
				.getCategoriesByVisibleType(VisibilityType.ALL);

		assertThat(transactionCategories).hasSize(2).extracting(TransactionCategory::getVisibilityType)
				.allMatch(x -> x == VisibilityType.ALL);
	}

}
