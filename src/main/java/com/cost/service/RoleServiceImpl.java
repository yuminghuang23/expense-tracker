package com.cost.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cost.model.Role;
import com.cost.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Boolean delete(int id) {
		boolean deleted = false;
		if (roleRepository.existsById(id)) {
			roleRepository.deleteById(id);
			deleted = true;
		}
		return deleted;
	}

	@Override
	public Role update(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role findById(int id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public Role findByUserName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public Collection<Role> findAll() {
		return (Collection<Role>) roleRepository.findAll();
	}
}
