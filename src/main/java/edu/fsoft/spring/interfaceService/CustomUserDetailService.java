package edu.fsoft.spring.interfaceService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.MyUserDetail;
import edu.fsoft.spring.repository.AccountRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account acc = repo.findByUsername(username);
		if(acc != null) {
			/*
			List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority(acc.getRole());
			grantList.add(authority);
			UserDetails userDetails = new User(acc.getUsername(), acc.getPassword(), grantList);
			*/
			
			UserDetails userDetails = User
					.withUsername(acc.getUsername())
					.password(acc.getPassword())
					.roles(acc.getRole())				
					.build();
		
			return userDetails;
		} 
		else {
			throw new UsernameNotFoundException("Could not find user!");
		}
	}
}
