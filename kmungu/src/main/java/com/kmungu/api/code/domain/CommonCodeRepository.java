package com.kmungu.api.code.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * CommonCodeRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodePK> {

	List<CommonCode> findByGroupCd(String groupCd);
	
	@Query(value = "SELECT cc FROM CommonCode cc where cc.groupCd like 'p_category%' order by cc.code")
	List<CommonCode> getCategoryAll();
	
}