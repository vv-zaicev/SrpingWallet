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

import com.zaicev.spring.transactions.dao.TransactionDAO;
import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.wallet.dao.WalletDAO;
import com.zaicev.spring.wallet.models.Wallet;

@RunWith(MockitoJUnitRunner.class)
public class TestTransactionController {

	private MockMvc mockMvc;

	@Mock
	private TransactionDAO transactionDAO;

	@Mock
	private WalletDAO walletDAO;

	@InjectMocks
	private TransactionController transactionController;

	@Before
	public void initialize() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	public void testShowTransaction() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setId(1);
		when(transactionDAO.getTransactionById(1)).thenReturn(transaction);

		mockMvc.perform(get("/transaction/1")).andExpect(status().isOk()).andExpect(view().name("transactions/show"))
				.andExpect(model().attributeExists("transaction"));
	}

	@Test
	public void testDeleteTransaction() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setId(1);
		Wallet wallet = new Wallet();
		wallet.setId(1);
		wallet.addTransaction(transaction);
		when(transactionDAO.getTransactionById(1)).thenReturn(transaction);
		
		mockMvc.perform(delete("/transaction/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/wallet/1"));
	}

}
