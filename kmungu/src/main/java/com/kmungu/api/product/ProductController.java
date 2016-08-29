package com.kmungu.api.product;


import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.product.domain.Product;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${km.upload.location}")
	private String uploadPath;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductController() {
		// TODO Auto-generated constructor stub
	}
	/*
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<Product> list = service.getProductAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}*/
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	//public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	public DataTablesOutput<Product> getList(@Valid DataTablesInput input){
	/*
		Page<Product> list = service.getProductList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
		*/

		return service.getProductList(input);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Product product, @RequestParam(name = "imgFiles", required = false) MultipartFile[] imgFiles){
		
		try {
			for (int i = 0; i < imgFiles.length; i++) {
				File uploadedFile = new File(uploadPath + imgFiles[i].getOriginalFilename());
				imgFiles[i].transferTo(uploadedFile);
				
				if (i == 0) {
					product.setImg1Path(uploadedFile.getAbsolutePath());
				} else if (i == 1) {
					product.setImg2Path(uploadedFile.getAbsolutePath());
				} else if (i == 2) {
					product.setImg3Path(uploadedFile.getAbsolutePath());
				}
				
			}
			
			//gstarContents.setUrl(videoId);
			//gstarContents.setLocale(locale.getLanguage());
			service.save(product);
			
		
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
			jsonRes.setSuccess(false);
			jsonRes.setMsg("");
		}
		
		
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		
		
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