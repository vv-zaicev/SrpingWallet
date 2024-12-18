package com.zaicev.spring.wallet.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WalletMapper implements RowMapper<Wallet>{

	@Override
	public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
		Wallet wallet = new Wallet();
		wallet.setName(rs.getString("wallet_name"));
		wallet.setId(rs.getInt("wallet_id"));
		wallet.setBalance(rs.getBigDecimal("wallet_balance"));		
		return wallet;
	}
	
	

}
