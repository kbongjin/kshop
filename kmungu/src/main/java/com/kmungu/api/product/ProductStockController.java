package com.kmungu.api.product;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.product.domain.ProductStock;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/product/stock")
public class ProductStockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductStockController.class);
	
	@Autowired
	private ProductStockService service;
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductStockController() {
		
	}
	
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public DataTablesOutput<ProductStock> getList(@Valid DataTablesInput input, @RequestParam(name="productId") Integer productId){
	
		return service.getProductStockList(input, productId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, ProductStock productStock){
		
		service.save(productStock);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productStockId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("productStockId") Integer productStockId){
		
		service.deleteProductStock(productStockId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productStockId}", method = RequestMethod.GET)
	public String getProductStock(Model model, @PathVariable("productStockId") Integer productStockId, Integer productId){
	
		if (productStockId != null && productStockId > 0) {
			
			ProductStock stock = service.getProductStock(productStockId);
			
			model.addAttribute("product", pService.getProduct(stock.getProductId()));
			model.addAttribute("stock", stock);
			model.addAttribute("productId", stock.getProductId());
		} else {
			model.addAttribute("product", pService.getProduct(productId));
			model.addAttribute("productId", productId);
		}
		
		
		return "productStockForm";
	}

}
//end of ProductStockController.java