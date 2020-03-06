package com.cost.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cost.model.User;
import com.cost.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Boolean delete(int id) {
		boolean deleted = false;
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			deleted = true;
		}
		return deleted;
	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Collection<User> findAll() {
		return (Collection<User>) userRepository.findAll();
	}

}
