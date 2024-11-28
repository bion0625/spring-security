package com.small.buissness.config;

import com.small.buissness.authentication.otp.OtpAuthenticationProvider;
import com.small.buissness.authentication.user.UsernamePasswordAuthenticationProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final String signingKey = String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS512));

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(otpAuthenticationProvider, usernamePasswordAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);

        http.csrf(AbstractHttpConfigurer::disable);

        http
                .addFilterAt(new InitialAuthenticationFilter(authenticationManager(), signingKey), BasicAuthenticationFilter.class)
                .addFilterAt(new JwtAuthenticationFilter(signingKey), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        return http.build();
    }
}
