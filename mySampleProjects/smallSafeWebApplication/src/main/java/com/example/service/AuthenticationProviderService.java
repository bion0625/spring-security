package com.example.service;

import com.example.domain.dto.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailService userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication
                .getCredentials()
                .toString();

        CustomUserDetail user = userDetailService.loadUserByUsername(username);

        switch (user.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }

        throw new BadCredentialsException("Bad Credentials");
    }

    private Authentication checkPassword(CustomUserDetail user, String rawPassword, PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        else throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
