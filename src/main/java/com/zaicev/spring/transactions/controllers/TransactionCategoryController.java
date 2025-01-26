package com.zaicev.spring.transactions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zaicev.spring.models.VisibilityType;
import com.zaicev.spring.security.UserDetailsImpl;
import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.security.models.User;
import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.models.TransactionCategory;

@Controller
@RequestMapping("/transactioncategory")
public class TransactionCategoryController {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TransactionCategoryDAO transactionCategoryDAO;

	@GetMapping("/all")
	public String adminIndex(Model model) {
		model.addAttribute("categories", transactionCategoryDAO.getCategoriesByVisibleType(VisibilityType.ALL));
		model.addAttribute("all", true);
		return "transactionCategory/index";
	}

	@GetMapping()
	public String userIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		model.addAttribute("categories", user.getTransactionCategories());
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
	public String create(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		transactionCategory.setUser(user);
		transactionCategory.setVisibilityType(VisibilityType.PERSONAL);

		transactionCategoryDAO.createCategory(transactionCategory);
		return "redirect:/transactioncategory";
	}

	@PostMapping("/all")
	public String createForAll(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory) {
		transactionCategory.setVisibilityType(VisibilityType.ALL);
		transactionCategoryDAO.createCategory(transactionCategory);
		return "redirect:/transactioncategory/all";
	}

	@PatchMapping()
	public String update(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		transactionCategory.setUser(user);
		transactionCategory.setVisibilityType(VisibilityType.PERSONAL);

		transactionCategoryDAO.updateCategory(transactionCategory);
		return "redirect:/transactioncategory";
	}

	@PatchMapping("/all")
	public String updateForAll(@ModelAttribute("transactionCategory") TransactionCategory transactionCategory) {
		transactionCategory.setVisibilityType(VisibilityType.ALL);
		transactionCategoryDAO.updateCategory(transactionCategory);
		return "redirect:/transactioncategory/all";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		transactionCategoryDAO.deleteCategory(id);
		return "redirect:/transactioncategory";
	}

}
