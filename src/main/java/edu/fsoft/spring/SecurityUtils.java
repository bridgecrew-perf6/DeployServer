package edu.fsoft.spring;

import org.springframework.security.core.context.SecurityContextHolder;

import edu.fsoft.spring.model.Account;

public class SecurityUtils {
	public String getPrincipal() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return username;
	}
}
