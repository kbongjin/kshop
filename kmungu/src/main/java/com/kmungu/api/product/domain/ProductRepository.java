package com.kmungu.api.product.domain;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface ProductRepository extends DataTablesRepository<Product, Integer>{

	@Modifying
	@Query(value = "update Product g set g.stockQty = (select sum(s.stockQty) from ProductStock s where s.productId = ?1) where g.id = ?1")
	int updateProductStockQty(Integer productId);
	
}