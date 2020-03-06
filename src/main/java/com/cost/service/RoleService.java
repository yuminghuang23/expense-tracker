package com.cost.service;

import java.util.Collection;

import com.cost.model.Role;


public interface RoleService {
	Role save(Role role);

    Boolean delete(int id);

    Role update(Role role);

    Role findById(int id);

    Role findByUserName(String roleName);

    Collection<Role> findAll();
}
