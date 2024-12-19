package com.zaicev.spring.transactions.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.zaicev.spring.transactions.models.TransactionCategory;

@Component
public class TransactionCategoryDAO {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert insertIntoTransactionCategory;
	
	private final String SQL_SELECT_CATEGORIES = "SELECT * FROM transaction_category";
	private final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM transaction_category WHERE category_id=?";
	private final String SQL_UPDATE_CATEGORY = "UPDATE transaction_category SET category_name=? WHERE category_id=?";
	private final String SQL_DELETE_CATEGORY = "DELETE FROM transaction_category WHERE category_id=?";
	
	private final RowMapper<TransactionCategory> transactionCategoryMapper = (rs, rowNum) -> {
		String name = rs.getString("category_name");
		int id = rs.getInt("category_id");
		return new TransactionCategory(name, id);
	};
	
	@Autowired
	public TransactionCategoryDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		insertIntoTransactionCategory = new SimpleJdbcInsert(jdbcTemplate).withTableName("transaction_category").usingGeneratedKeyColumns("category_id");
	}
	
	public List<TransactionCategory> getAllCategories(){
		return jdbcTemplate.query(SQL_SELECT_CATEGORIES, transactionCategoryMapper);
	}
	
	public TransactionCategory getCategoryById(int id) {
		return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY_BY_ID, transactionCategoryMapper, id);
	}
	
	public void createCategory(TransactionCategory newCategory) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("category_name", newCategory.getName());
		int id = insertIntoTransactionCategory.executeAndReturnKey(parameters).intValue();
		newCategory.setId(id);
	}
	
	public void updateCategory(TransactionCategory updatedCategory) {
		jdbcTemplate.update(SQL_UPDATE_CATEGORY, updatedCategory.getName(), updatedCategory.getId());
	}
	
	public void deleteCategory(int id) {
		jdbcTemplate.update(SQL_DELETE_CATEGORY, id);
	}
}
