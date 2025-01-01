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
import org.springframework.web.bind.annotation.RequestParam;

import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.transactions.dao.TransactionDAO;
import com.zaicev.spring.transactions.models.Transaction;
import com.zaicev.spring.wallet.dao.WalletDAO;
import com.zaicev.spring.wallet.models.Wallet;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	private TransactionDAO transactionDAO;
	@Autowired
	private TransactionCategoryDAO transactionCategoryDAO;
	@Autowired
	private WalletDAO walletDAO;

	public String index(Model model, @ModelAttribute("wallet") Wallet wallet) {
		model.addAttribute("transactions", transactionDAO.getAllTransactions());
		return null;
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		model.addAttribute("transaction", transactionDAO.getTransactionById(id));
		return "transactions/show";
	}

	@GetMapping("/new")
	public String newTransaction(@RequestParam("wallet_id") int walletId, Model model) {
		model.addAttribute("transaction", new Transaction());
		model.addAttribute("categories", transactionCategoryDAO.getAllCategories());
		model.addAttribute("walletId", walletId);
		return "transactions/new";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		Transaction transaction = transactionDAO.getTransactionById(id);

		model.addAttribute("transaction", transaction);
		model.addAttribute("categories", transactionCategoryDAO.getAllCategories());

		return "transactions/edit";
	}

	@PostMapping()
	public String create(@ModelAttribute("transaction") Transaction transaction,
			@RequestParam("wallet_id") int walletId) {
		Wallet wallet = walletDAO.getWalletById(walletId);

		transaction.setWallet(wallet);

		wallet.addTransaction(transaction);
		walletDAO.updateWallet(wallet);
		transactionDAO.createTransaction(transaction);

		return "redirect:/wallet/" + wallet.getId();
	}

	@PatchMapping()
	public String update(@ModelAttribute("transaction") Transaction transaction) {
		Wallet wallet = transaction.getWallet();

		wallet.updateTransaction(transaction);
		walletDAO.updateWallet(wallet);
		transactionDAO.updateTransaction(transaction);

		return "redirect:/wallet/" + transaction.getWallet().getId();
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		Transaction transaction = transactionDAO.getTransactionById(id);
		Wallet wallet = transaction.getWallet();

		wallet.removeTransaction(transaction);
		walletDAO.updateWallet(wallet);
		transactionDAO.deleteTransaction(transaction.getId());

		return "redirect:/wallet/" + wallet.getId();
	}
}
