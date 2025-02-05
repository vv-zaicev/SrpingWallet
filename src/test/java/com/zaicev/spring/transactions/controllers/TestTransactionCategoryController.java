package com.zaicev.spring.transactions.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.models.TransactionCategory;

@RunWith(MockitoJUnitRunner.class)
public class TestTransactionCategoryController {

	@Mock
	private UserDAO userDAO;

	@Mock
	private TransactionCategoryDAO transactionCategoryDAO;

	@InjectMocks
	private TransactionCategoryController transactionCategoryController;

	private MockMvc mockMvc;

	@Before
	public void initialize() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionCategoryController).build();
	}

	@Test
	public void testEditTransactionCategory() throws Exception {
		TransactionCategory transactionCategory = new TransactionCategory("something", 1);
		
		when(transactionCategoryDAO.getCategoryById(1)).thenReturn(transactionCategory);

		mockMvc.perform(get("/transactioncategory/1/edit")).andExpect(status().isOk())
				.andExpect(view().name("transactionCategory/edit"))
				.andExpect(model().attributeExists("transactionCategory"));
	}

	@Test
	public void testDeleteTransactionCategory() throws Exception {
		mockMvc.perform(delete("/transactioncategory/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/transactioncategory"));
	}
}
