package edu.fsoft.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import edu.fsoft.spring.model.Account;
@Repository	
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByUsernameAndPassword(String userName, String password);
	List<Account> findByRole(String role);
	@Query(value = "SELECT * FROM ACCOUNT WHERE username = :username", nativeQuery = true)
	List<Account> findUsername(String username);
	Account findByUsername(String phone);
	Account findByPhone(String phone);
	Account findByEmail(String email);
}
