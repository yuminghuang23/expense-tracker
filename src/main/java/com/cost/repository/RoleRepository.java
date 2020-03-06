package com.cost.repository;

import org.springframework.data.repository.CrudRepository;

import com.cost.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	Role findByRoleName(String roleName);
}
