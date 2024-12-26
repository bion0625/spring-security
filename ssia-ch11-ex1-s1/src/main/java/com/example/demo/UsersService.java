package com.example.demo;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsersService {

	private final PasswordEncoder encoder;
	private final UsersRepository usersRepository;
	private final OtpRepository otpRepository;

	public UsersService(PasswordEncoder encoder, UsersRepository usersRepository, OtpRepository otpRepository) {
		this.encoder = encoder;
		this.usersRepository = usersRepository;
		this.otpRepository = otpRepository;
	}

	public void addUser(Users users) {
		users.setPassword(encoder.encode(users.getPassword()));
		usersRepository.save(users);
	}

	public void auth(Users users) {
		usersRepository.findUsersByUsername(users.getUsername())
				.filter(u -> encoder.matches(users.getPassword(), u.getPassword())).map(u -> {
					renewOtp(u);
					return true;
				}).orElseThrow(() -> new BadCredentialsException("Bad credentials."));
	}
	
	public boolean check(Otp otpToValidate) {
		return otpRepository.findOtpByUsername(otpToValidate.getUsername())
				.filter(otp -> otpToValidate.getCode().equals(otp.getCode())).isPresent();
	}

	private void renewOtp(Users users) {
		String code = GenerateCodeUtil.generateCode();
		String username = users.getUsername();

		otpRepository.findOtpByUsername(username).map(otp -> {
			otp.setCode(code);
			return true;
		}).orElseGet(() -> {
			Otp otp = new Otp();
			otp.setUsername(username);
			otp.setCode(code);
			otpRepository.save(otp);
			return true;
		});
	}
}
