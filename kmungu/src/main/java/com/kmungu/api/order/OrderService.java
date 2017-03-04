package com.kmungu.api.order;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.kmungu.api.order.domain.OrderTbl;
import com.kmungu.api.order.domain.OrderTblRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderTblRepository repository;
	
	public OrderService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(OrderTbl order){
		repository.save(order);
	}
	
	public DataTablesOutput<OrderTbl> getOrderList(DataTablesInput input) {
	
		/*
		Specifications<Order> spec = Specifications.where(OrderSpecs.notBattle()).and(OrderSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(OrderSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(input);
	}
	
	/*
	public int getOrderListTotalCount(GridParam gridParam){
		
		return repository.getOrderListTotalCount(gridParam);
	}
	*/
	
	public OrderTbl getOrder(String orderNo){
		return repository.findOne(orderNo);
	}
	
	/*
	public void updateOrder(Order order){
		repository.updateOrder(order);
	}
	*/
	
	public void deleteOrder(String orderNo){
		repository.delete(orderNo);
	}

}
//end of OrderService.java