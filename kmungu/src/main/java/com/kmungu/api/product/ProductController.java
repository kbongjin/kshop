package com.kmungu.api.product;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductController() {
	}

	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getList(Model model, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable){
	
		Page<Product> list = service.getProductList(pageable);

		model.addAttribute("paging", list);
		
		return "productList";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Product product){
		
		service.save(product);
		
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
	public String getProduct(Model model, @PathVariable("productId") Integer productId, Product product){
	
		if (productId > 0) {
			product = service.getProduct(productId);
		}
		model.addAttribute("product", product);
		
		return "productForm";
	}
	

}
//end of ProductController.java