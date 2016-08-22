package com.kmungu.api.user.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kmungu.api.user.domain.AccountAuth;

/**
 * AccountAuthRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface AccountAuthRepository extends JpaRepository<AccountAuth, Integer> {

	
}