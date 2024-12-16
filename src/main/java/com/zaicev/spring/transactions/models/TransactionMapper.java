package com.zaicev.spring.transactions.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TransactionMapper implements RowMapper<Transaction>{

	@Override
	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
