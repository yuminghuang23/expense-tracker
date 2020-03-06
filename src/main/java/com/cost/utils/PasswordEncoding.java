package com.cost.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoding {
	
	private static PasswordEncoding passwordEncoding = new PasswordEncoding();
	public BCryptPasswordEncoder passwordEncoder;
	
	public static PasswordEncoding getInstance() {
		if (passwordEncoding != null) return passwordEncoding;
		return new PasswordEncoding();
	}
	
	private PasswordEncoding() {
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public boolean match(String oldPassword, String dbPassword) {
		return passwordEncoder.matches(oldPassword, dbPassword);
	}
}
