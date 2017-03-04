package com.kmungu.api.product;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.kmungu.api.code.domain.CommonCode;
import com.kmungu.api.code.domain.CommonCodeRepository;
import com.kmungu.api.product.domain.Product;
import com.kmungu.api.product.domain.ProductMaster;
import com.kmungu.api.product.domain.ProductMasterRepository;
import com.kmungu.api.product.domain.ProductRepository;
import com.kmungu.api.product.spec.ProductSpecs;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CommonCodeRepository codeRepo;
	
	@Autowired
	private ProductMasterRepository pmRepo;
	
	public ProductService() {
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	public void save(Product product){
		
		
		ProductMaster pm = pmRepo.findOne(product.getProductMasterId());
		
		int pmRetailPrice = pm.getRetailPrice();
		
		if (pmRetailPrice > 0 && pmRetailPrice > product.getRetailPrice()) {
			
			pm.setRetailPrice(product.getRetailPrice());
			
			pmRepo.save(pm);
		}
		
		repository.save(product);
	}
	
	public Page<Product> getProductList(Pageable pageable){
	
		
		return repository.findAll(pageable);
	}
	
	public List<Product> getProductList(Integer productMasterId){
		
		
		Specifications<Product> spec = Specifications.where(ProductSpecs.eqProductMasterId(productMasterId));
		
		return repository.findAll(spec);
		
	
	}
	
	public Product getProduct(Integer productId){
		return repository.findOne(productId);
	}
	
	/*
	public void updateProduct(Product product){
		repository.updateProduct(product);
	}
	*/
	
	public void deleteProduct(Integer productId){
		
		repository.delete(productId);
	}
	
	public List<CommonCode> getCategoryAll() {
		return codeRepo.getCategoryAll();
	}

}
//end of ProductService.java