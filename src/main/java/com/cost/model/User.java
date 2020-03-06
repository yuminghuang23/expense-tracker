package com.cost.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true)
	private int userId;
	
	@Column(name = "username")
	private String username; 
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private int role;

	public User(int userId, String userName, String email, String password, int role) {
		super();
		this.userId = userId;
		this.username = userName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public User() {}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	} 
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User)o;
        return userId == user.userId &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) && 
        		role == user.role;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userId, username, email, role);
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", role=" + role + "]";
	}
}
