package com.kmungu.api.category.domain;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * CategoryRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

	Page<Category> findByLevel(int level, Pageable pageable);
	
	@EntityGraph(value = "Category.detail", type = EntityGraphType.LOAD)
	Set<Category> findByLevelOrderByOrderSeq(int level);
	
	Page<Category> findByParentCtgId(Integer parentCtgId, Pageable pageable);
	
	@Query(value = "SELECT c1 FROM Category c1 LEFT OUTER JOIN c1.children c2 LEFT OUTER JOIN c2.children c3 WHERE c1.level = 1 Order by c1.orderSeq, c2.orderSeq, c3.orderSeq")
	Set<Category> findTopNav();
}