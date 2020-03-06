package com.cost.repository;

import org.springframework.data.repository.CrudRepository;

import com.cost.model.PasswordResetToken;


public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {
	
	PasswordResetToken findByToken(String token);
	
	void deleteByToken(String token);
}
