package com.kmungu.api.product;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.product.domain.Product;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService service;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> allList(){
	
		List<Product> list = service.getProductAllList();
		
		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Product product){
		
		service.save(product);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("productId") Integer productId){
		
		service.deleteProduct(productId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getProduct(SimpleJsonResponse jsonRes, @PathVariable("productId") Integer productId){
	
		jsonRes.setData(service.getProduct(productId));
		
		return jsonRes;
	}

}
//end of ProductController.java