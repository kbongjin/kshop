package com.kmungu.api.product.domain;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductStockRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
public interface ProductStockRepository extends DataTablesRepository<ProductStock, Integer> {

	
}