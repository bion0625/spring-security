package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private CustomAuthenticationProvider customAuthenticationProvider;

	public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
		this.customAuthenticationProvider = customAuthenticationProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(customAuthenticationProvider);
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
