package edu.fsoft.spring.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetail implements UserDetails {

	private Account acc;
	
	public MyUserDetail(Account acc) {
		this.acc = acc;
	}
	
	public MyUserDetail( ){
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(acc.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return acc.getPassword();
	}

	@Override
	public String getUsername() {
		return acc.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
