package com.kmungu.api.product;

import java.util.List;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.kmungu.api.product.domain.ProductImg;
import com.kmungu.api.product.domain.ProductImgRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class ProductImgService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImgService.class);

	@Autowired
	private ProductImgRepository repository;
	
	public ProductImgService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(ProductImg productImg){
		repository.save(productImg);
	}
	
	public List<ProductImg> getProductImgAllList(){
		return repository.findAll();
	}
	
	public Page<ProductImg> getProductImgList(Pageable pageable, String search){
	
		/*
		Specifications<ProductImg> spec = Specifications.where(ProductImgSpecs.notBattle()).and(ProductImgSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(ProductImgSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(pageable);
	}
	
	public ProductImg getProductImg(Integer productImgKey){
		return repository.findOne(productImgKey);
	}
	
	//@Transactional
	public void deleteProductImg(Integer productImgKey){
		repository.delete(productImgKey);
	}

}
//end of ProductImgService.java