package edu.fsoft.spring.interfaceService;

import java.util.List;

import edu.fsoft.spring.model.Account;

public interface IAccountService {

	Account findByUsernameAndPassword(String userName, String password);
	Account findByUsername(String userName);
	List<Account> findByRole(String role);
	public Account get(Long id);
	void save(Account account);
	public Account findByPhone(String phone);
	public Account findByEmail(String email);
}
