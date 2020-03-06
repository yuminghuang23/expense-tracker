package com.cost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cost.model.User;
import com.cost.service.UserService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GlobalController {
	
	@Autowired
	private UserService userService;
	
	private User loginUser;
	
	public User getLoginUser() {
		if (loginUser == null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			loginUser = userService.findByUserName(auth.getName());
		}
		return loginUser;
	}
}
