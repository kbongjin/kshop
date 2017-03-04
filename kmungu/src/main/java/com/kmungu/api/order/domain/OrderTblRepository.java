package com.kmungu.api.order.domain;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
public interface OrderTblRepository extends DataTablesRepository<OrderTbl, String> {

	
}