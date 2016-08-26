package com.kmungu.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * GstarUserRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByLoginId(String loginId);
}