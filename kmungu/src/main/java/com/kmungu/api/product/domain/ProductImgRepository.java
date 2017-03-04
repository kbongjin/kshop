package com.kmungu.api.product.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kmungu.api.product.domain.ProductImg;

/**
 * ProductImgRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer>, JpaSpecificationExecutor<ProductImg> {

	
}