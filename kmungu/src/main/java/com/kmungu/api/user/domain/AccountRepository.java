package com.kmungu.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GstarAccountRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByLoginId(String loginId); 
	
}