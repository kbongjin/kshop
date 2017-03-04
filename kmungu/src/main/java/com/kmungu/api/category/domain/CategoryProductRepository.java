package com.kmungu.api.category.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kmungu.api.category.domain.CategoryProduct;

/**
 * CategoryProductRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer>, JpaSpecificationExecutor<CategoryProduct> {

	Long deleteByProductMasterId(Integer productMasterId);
	
}