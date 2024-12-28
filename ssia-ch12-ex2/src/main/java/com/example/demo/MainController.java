package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String main(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
		System.out.println("user: " + user);
		System.out.println();
		return "main.html";
	}
}
