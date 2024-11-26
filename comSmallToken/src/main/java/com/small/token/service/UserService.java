package com.small.token.service;

import com.small.token.entity.Otp;
import com.small.token.entity.User;
import com.small.token.repository.OtpRepository;
import com.small.token.repository.UserRepository;
import com.small.token.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpRepository otpRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> o = userRepository.findUserByUsername(user.getUsername());
        if (o.isPresent()) {
            User u = o.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else throw new BadCredentialsException("Bad credentials.");
        } else throw new BadCredentialsException("Bad credentials.");
    }

    private void renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findUserByUsername(u.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOpt = otpRepository.findUserByUsername(otpToValidate.getUsername());

        if (userOpt.isPresent()) {
            Otp otp = userOpt.get();
            if (otpToValidate.getCode().equals(otp.getCode())) return true;
        }

        return false;
    }
}
