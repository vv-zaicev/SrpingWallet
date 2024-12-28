package com.zaicev.spring.transactions.conversion;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.models.TransactionCategory;

public class TransactionCategoryConverter implements Formatter<TransactionCategory> {

	private final TransactionCategoryDAO transactionCategoryDAO;

	public TransactionCategoryConverter(TransactionCategoryDAO transactionCategoryDAO) {
		this.transactionCategoryDAO = transactionCategoryDAO;
	}

	@Override
	public String print(TransactionCategory object, Locale locale) {
		System.out.println(object.getId());
		return (object != null ? Integer.toString(object.getId()) : "");
	}

	@Override
	public TransactionCategory parse(String text, Locale locale) throws ParseException {
		int categoryId = Integer.valueOf(text);
		return transactionCategoryDAO.getCategoryById(categoryId);
	}

}
