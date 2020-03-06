package com.cost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cost.model.PasswordResetToken;
import com.cost.repository.PasswordResetTokenRepository;

@Service
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
	
	@Autowired
	private PasswordResetTokenRepository passwordRestTokenRepository;

	@Override
	public PasswordResetToken save(PasswordResetToken passwordResetToken) {
		return passwordRestTokenRepository.save(passwordResetToken);
	}
	
	@Override
	public PasswordResetToken findByToken(String token) {
		return passwordRestTokenRepository.findByToken(token);
	}

	@Override
	public void deleteByToken(String token) {
		passwordRestTokenRepository.deleteByToken(token);
	};
}
