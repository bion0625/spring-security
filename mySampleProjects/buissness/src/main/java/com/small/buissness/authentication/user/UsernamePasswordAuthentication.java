package com.small.buissness.authentication.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities) {

        super(principal, credentials, authorities);
    }

    public UsernamePasswordAuthentication(
            Object principal,
            Object credentials) {

        super(principal, credentials);
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
