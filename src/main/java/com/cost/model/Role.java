package com.cost.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cost.utils.Roles;


@Entity
@Table(name = "Role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int roleId; 
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private Roles roleName;

	public Role(int roleId, Roles roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public Roles getRoleName() {
		return roleName;
	}
	public void setRoleName(Roles roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Role role = (Role) o;
		return roleId == role.roleId && Objects.equals(roleName, role.roleName);
	}
}
