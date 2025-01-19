package com.zaicev.spring.security.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zaicev.spring.security.dao.UserDAO;
import com.zaicev.spring.security.models.User;

@Controller
public class SecurityController {

	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityController(UserDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		return "security/login";
	}

	@GetMapping("/register")
	public String registrationPage(Model model) {
		model.addAttribute("user", new User());
		return "security/register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRoles(Set.of("USER"));
		userDAO.createUser(user);

		return "redirect:/login";
	}

}
