package com.kmungu.api.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmungu.api.user.domain.PassresetToken;
import com.kmungu.api.user.domain.PassresetTokenRepository;
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
public class UserService implements UserDetailsService {
	
	private static final int EXPIRATION = 60 * 24;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PassresetTokenRepository tokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserService() {
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLoginId(username);
	}
	
	@Transactional
	public String createPasswordResetToken(User account) { 
		
		account.setLocked(true);
		repository.save(account);
		
		String token = UUID.randomUUID().toString();
		
		PassresetToken tokenEntity = new PassresetToken(account.getId(), token, calculateExpiryDate());
		
		tokenRepo.save(tokenEntity);
		
		return token;
	}
	
	private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
	
	public PassresetToken getPasswordResetToken(String token) {
		return tokenRepo.findByToken(token);
	}
	
	@Transactional
	public void changeUserPassword(User user, String password) {
		
		user.setLocked(false);
		user.setPasswd(passwordEncoder.encode(password));
		
		repository.save(user);
		
		tokenRepo.updateExpireDt(user.getId());
		
	}

}
//end of GstarUserService.java