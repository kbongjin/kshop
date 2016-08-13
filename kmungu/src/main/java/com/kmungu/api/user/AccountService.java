package com.kmungu.api.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmungu.api.user.domain.Account;
import com.kmungu.api.user.domain.AccountRepository;
import com.kmungu.api.user.domain.PassresetToken;
import com.kmungu.api.user.domain.PassresetTokenRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class AccountService implements UserDetailsService {
	
	private static final int EXPIRATION = 60 * 24;

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private PassresetTokenRepository tokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AccountService() {
		
	}
	
	public void save(Account gstarAccount){
		repository.save(gstarAccount);
	}
	
	public List<Account> getGstarAccountAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarAccountListTotalCount(GridParam gridParam){
		
		return repository.getGstarAccountListTotalCount(gridParam);
	}
	*/
	
	public Account getGstarAccount(Long accountId){
		return repository.findOne(accountId);
	}

	
	public void deleteGstarAccount(Long accountId){
		repository.delete(accountId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLoginId(username);
	}
	
	@Transactional
	public String createPasswordResetToken(Account account) { 
		
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
	public void changeUserPassword(Account gstarAccount, String password) {
		
		gstarAccount.setLocked(false);
		gstarAccount.setPasswd(passwordEncoder.encode(password));
		
		repository.save(gstarAccount);
		
		tokenRepo.updateExpireDt(gstarAccount.getId());
		
	}

}
//end of GstarAccountService.java