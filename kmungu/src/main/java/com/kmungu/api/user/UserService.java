package com.kmungu.api.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmungu.api.user.domain.Account;
import com.kmungu.api.user.domain.User;
import com.kmungu.api.user.domain.UserRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AccountService accountService;
	
	public UserService() {
	}
	
	@Transactional
	public void join(User user, Account account){
		
		repository.save(user);
		
		account.setUser(user);
		
		accountService.save(account);
		
	}
	
	public void save(User user){
		repository.save(user);
	}
	
	public List<User> getGstarUserAllList(){
		return repository.findAll();
	}
	
	public User getUser(Long userId){
		return repository.findOne(userId);
	}
	
	
	public void deleteUser(Long userId){
		repository.delete(userId);
	}

}
//end of GstarUserService.java