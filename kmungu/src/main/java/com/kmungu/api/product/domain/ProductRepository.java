package com.kmungu.api.product.domain;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface ProductRepository extends DataTablesRepository<Product, Integer>{

	
}