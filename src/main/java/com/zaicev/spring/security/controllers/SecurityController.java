package com.zaicev.spring.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	@GetMapping("/login")
	public String loginPage(Model model) {
		return "security/login";
	}

}
