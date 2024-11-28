package com.small.buissness.authentication.otp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {
    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
