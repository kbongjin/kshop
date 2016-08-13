package com.kmungu.api.product.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kmungu.api.product.domain.Product;

/**
 * ProductRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	
}