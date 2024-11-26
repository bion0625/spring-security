package com.small.token.repository;

import com.small.token.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findUserByUsername(String username);
}
