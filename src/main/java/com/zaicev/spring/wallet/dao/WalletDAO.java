package com.zaicev.spring.wallet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zaicev.spring.wallet.models.Wallet;

@Component
public class WalletDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public WalletDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Wallet> index(){
		return null;
	}
	
	public Wallet get(int id) {
		return null;
	}
	
	public void save(Wallet newWallet) {
		
	}
	
	public void update(Wallet updatedWallet) {
		
	}
	
	public void delete(int id) {
		
	}
}
