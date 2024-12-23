package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login
				.successHandler(new CustomAuthenticationSuccessHandler())
				.failureHandler(new CustomAuthenticationFailureHandler()));
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
