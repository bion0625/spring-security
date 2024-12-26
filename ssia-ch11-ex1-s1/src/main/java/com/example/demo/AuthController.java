package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
	private UsersService usersService;

	public AuthController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping("/user/add")
	public void addUser(@RequestBody Users users) {
		usersService.addUser(users);
	}

	@PostMapping("/user/auth")
	public void auth(@RequestBody Users users) {
		usersService.auth(users);
	}

	@PostMapping("/otp/check")
	public void check(@RequestBody Otp otp, HttpServletResponse response) {
		if (usersService.check(otp))
			response.setStatus(HttpServletResponse.SC_OK);
		else
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
}
