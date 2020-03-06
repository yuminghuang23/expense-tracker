package com.cost.service;

import com.cost.model.PasswordResetToken;

public interface PasswordResetTokenService {
	
	PasswordResetToken save(PasswordResetToken passwordResetToken);
	
	PasswordResetToken findByToken(String token);
	
	void deleteByToken(String token);
}
