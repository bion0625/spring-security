package com.example.demo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DummyUser implements UserDetails {

	@Override
	public String getUsername() {
		return "bill";
	}

	@Override
	public String getPassword() {
		return "12345";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> "read");
	}
}
