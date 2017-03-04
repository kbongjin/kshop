package com.kmungu.api.order;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.order.domain.OrderTbl;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public OrderController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public DataTablesOutput<OrderTbl> getList(@Valid DataTablesInput input){
	
		return service.getOrderList(input);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, OrderTbl order){
		
		service.save(order);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{orderId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("orderId") String orderNo){
		
		service.deleteOrder(orderNo);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getOrder(SimpleJsonResponse jsonRes, @PathVariable("orderId") String orderNo){
	
		jsonRes.setData(service.getOrder(orderNo));
		
		return jsonRes;
	}

}
//end of OrderController.java