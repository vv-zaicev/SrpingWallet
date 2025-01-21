package com.zaicev.spring.wallet.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

import com.zaicev.spring.security.UserDetailsImpl;
import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.security.models.User;
import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.models.TransactionFilter;
import com.zaicev.spring.wallet.dao.WalletDAO;
import com.zaicev.spring.wallet.models.Wallet;

@Controller
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletDAO walletDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TransactionCategoryDAO transactionCategoryDAO;

	@GetMapping()
	public String index(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		model.addAttribute("wallets", user.getWallets());
		return "wallet/index";
	}

	@GetMapping("/{id}")
	public String show(@ModelAttribute("filter") TransactionFilter transactionFilter, @PathVariable("id") int id,
			Model model) {
		Wallet wallet = walletDAO.getWalletById(id);
		wallet.setFilter(transactionFilter);

		BigDecimal divider = wallet.getIncome().max(wallet.getExpenses());
		BigDecimal incomePercent = new BigDecimal(100);
		BigDecimal expensesPercent = new BigDecimal(100);
		if (!divider.equals(BigDecimal.ZERO)) {
			incomePercent = wallet.getIncome().divide(divider, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
			expensesPercent = wallet.getExpenses().divide(divider, 2, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100));
		}

		model.addAttribute("wallet", wallet);
		model.addAttribute("categories", transactionCategoryDAO.getAllCategories());
		model.addAttribute("incomePercent", incomePercent);
		model.addAttribute("expensesPercent", expensesPercent);

		return "wallet/show";
	}

	@GetMapping("/new")
	public String create(Model model) {
		model.addAttribute("wallet", new Wallet());
		return "wallet/new";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("wallet", walletDAO.getWalletById(id));
		return "wallet/edit";
	}

	@PostMapping()
	public String create(@ModelAttribute("wallet") Wallet wallet,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		user.addWallet(wallet);
		userDAO.updateUser(user);
		return "redirect:/wallet";
	}

	@PatchMapping()
	public String update(@ModelAttribute("wallet") Wallet wallet) {
		walletDAO.updateWallet(wallet);
		return "redirect:/wallet";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDAO.getUserByName(userDetails.getUsername()).get();
		user.removeWallet(walletDAO.getWalletById(id));
		userDAO.updateUser(user);
		return "redirect:/wallet";
	}
}
