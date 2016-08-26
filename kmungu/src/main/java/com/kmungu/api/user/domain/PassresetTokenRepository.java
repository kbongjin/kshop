package com.kmungu.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GstarPassresetTokenRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface PassresetTokenRepository extends JpaRepository<PassresetToken, Integer> {

	PassresetToken findByToken(String token);
	
	@Modifying
	@Query("update PassresetToken pt set pt.expireDt = NOW() where pt.id = ?1")
	int updateExpireDt(Long userId);
	
}