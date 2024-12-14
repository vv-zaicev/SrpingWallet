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

import com.zaicev.spring.transactions.dao.TransactionDAO;
import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.wallet.models.Wallet;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	private TransactionDAO transactionDAO;

	@GetMapping()
	public String index(Model model, @ModelAttribute("wallet") Wallet wallet) {
		model.addAttribute("transactions", transactionDAO.index(wallet.getId()));
		return null; //json
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		model.addAttribute("transaction", transactionDAO.get(id));
		return "transactions/show";
	}

	@GetMapping("/new")
	public String newTransaction(Model model) {
		model.addAttribute("transaction", new Transaction());
		return "transactions/new";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("transaction", transactionDAO.get(id));
		return "transactions/edit";
	}

	@PostMapping()
	public String create(@ModelAttribute("transaction") Transaction transaction) {
		transactionDAO.save(transaction);
		return "redirect:/wallet/" + transaction.getWallet().getId();
	}

	@PatchMapping()
	public String update(@ModelAttribute("transaction") Transaction transaction) {
		transactionDAO.update(transaction);
		return "redirect:/wallet/" + transaction.getWallet().getId();
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		int walletId = transactionDAO.get(id).getWallet().getId();
		transactionDAO.delete(id);	
		return "redirect:/wallet/" + walletId;
	}
}
