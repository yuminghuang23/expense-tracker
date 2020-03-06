package com.cost.repository;

import org.springframework.data.repository.CrudRepository;

import com.cost.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);
	
	User findByEmail(String email);
}
