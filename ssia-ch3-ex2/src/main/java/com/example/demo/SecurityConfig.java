package com.example.demo;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable());
		http.authorizeHttpRequests(request -> request
				.requestMatchers(PathRequest.toH2Console()).permitAll()
				.anyRequest().authenticated());
		http.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService(DataSource dataSource) {
		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
		userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
		return userDetailsManager;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
