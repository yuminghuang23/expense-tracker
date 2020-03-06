package com.cost.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cost.model.User;
import com.cost.service.UserService;


@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userSerivce;
	
	private org.springframework.security.core.userdetails.User securityUser; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		boolean enabled = true;
		boolean accountNonLocked = true;
		boolean credentialsNonExpired = true;
		boolean accountNonExpired = true;
		
		User user = getUserDetail(username);
		if (user != null) {
			securityUser = new org.springframework.security.core.userdetails.User(user.getUsername(),
							user.getPassword(),
							enabled,
							accountNonExpired,
							accountNonLocked,
							credentialsNonExpired,
							getAuthorities(user.getRole())
							);
		return securityUser;
		}
		else {
			securityUser = new org.springframework.security.core.userdetails.User("empty", 
							"empty", 
							false, 
							accountNonExpired, 
							credentialsNonExpired, 
							false, 
							getAuthorities(1)
							);
			return securityUser;
		}
	}
	
	private User getUserDetail(String username) {
		User user = userSerivce.findByUserName(username);
		return user; 
	}
	
	public List<GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		if (role == 1) {
			authorityList.add(new SimpleGrantedAuthority("ADMIN"));
		}
		else if (role == 2) {
			authorityList.add(new SimpleGrantedAuthority("USER"));
		}
		return authorityList;
	}

}
