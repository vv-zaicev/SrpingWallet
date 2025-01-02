package com.zaicev.spring.transactions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.models.TransactionCategory;

@Controller
@RequestMapping("/transactioncategory")
public class TransactionCategoryController {
	@Autowired
	TransactionCategoryDAO transactionCategoryDAO;

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("categories", transactionCategoryDAO.getAllCategories());
		return "transactionCategory/index";
	}

	@GetMapping("/new")
	public String newCategory(Model model) {
		model.addAttribute("transactionCategory", new TransactionCategory());
		return "transactionCategory/new";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("transactionCategory", transactionCategoryDAO.getCategoryById(id));
		return "transactionCategory/edit";
	}

	@PostMapping()
	public String create(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory) {
		transactionCategoryDAO.createCategory(transactionCategory);
		return "redirect:/transactioncategory";
	}

	@PatchMapping()
	public String update(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory) {
		transactionCategoryDAO.updateCategory(transactionCategory);
		return "redirect:/transactioncategory";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		transactionCategoryDAO.deleteCategory(id);
		return "redirect:/transactioncategory";
	}

}
