package com.zaicev.spring.transactions.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zaicev.spring.transactions.models.Transaction;

@Component
public class TransactionDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TransactionDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Transaction> index(int walletId){
		return null;
	}
	
	public Transaction get(int id) {
		return null;
	}
	
	public void save(Transaction newTransaction) {
		
	}
	
	public void update(Transaction updatedTrasnaction) {
		
	}
	
	public void delete(int id) {
		
	}

}
