package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.oauth2Login(c -> c.clientRegistrationRepository(new InMemoryClientRegistrationRepository(clientRegistration())));
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
		return http.build();
	}
	
	ClientRegistration clientRegistration() {
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId("id")
				.clientSecret("secret")
				.build();
	}
}
