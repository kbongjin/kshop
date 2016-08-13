package com.kmungu.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmungu.api.product.domain.Product;
import com.kmungu.api.product.domain.ProductRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public ProductService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(Product product){
		repository.save(product);
	}
	
	public List<Product> getProductAllList(){
		return repository.findAll();
	}
	
	/*
	public int getProductListTotalCount(GridParam gridParam){
		
		return repository.getProductListTotalCount(gridParam);
	}
	*/
	
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

}
//end of ProductService.java