package com.zaicev.spring.transactions.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.jdbc.core.RowMapper;

public class TransactionMapper implements RowMapper<Transaction>{

	@Override
	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

		
		Transaction transaction = new Transaction();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(rs.getDate("transaction_date"));
		
		transaction.setId(rs.getInt("transaction_id"));
		transaction.setSum(rs.getBigDecimal("transaction_sum"));
		transaction.setType(TransactionType.valueOf(rs.getString("transaction_type")));
		transaction.setDate(calendar);
		transaction.setDescription(rs.getString("transaction_description"));
		
		TransactionCategory category = new TransactionCategory(rs.getString("category_name"), rs.getInt("category_id"));
		transaction.setCategory(category);
		
		return transaction;
	}
	

}
