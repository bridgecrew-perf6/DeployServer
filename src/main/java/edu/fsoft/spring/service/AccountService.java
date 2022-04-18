package edu.fsoft.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.fsoft.*;
import edu.fsoft.spring.interfaceService.IAccountService;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.repository.AccountRepository;
@Service
public class AccountService implements IAccountService {
	@Autowired
	private AccountRepository repo;
	
	@Autowired
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public Account findByUsernameAndPassword(String userName, String password) {
		return repo.findByUsernameAndPassword(userName, password);
	}

	public Account findByUsername(String userName) {
		return repo.findByUsername(userName);
	}
	public List<Account> findUsername(String userName) {
		return repo.findUsername(userName);
	}
	
	public List<Account> findByRole(String role) {
		return repo.findByRole(role);
	}
	
	public Account get(Long id) {
		return repo.findById(id).get();
	}
		
	public void save(Account account) {
		repo.save(account);
	}

	public void delete(long id) {
		repo.deleteById(id);
	}
	public Account findByPhone(String phone){
		return repo.findByPhone(phone);
	}
}
