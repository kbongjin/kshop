package com.kmungu.api.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.kmungu.api.code.domain.CommonCode;
import com.kmungu.api.code.domain.CommonCodeRepository;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CommonCodeRepository codeRepo;
	
	public ProductService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(Product product){
		repository.save(product);
	}
	
	public List<Product> getProductAllList(){
		return (List)repository.findAll();
	}
	
	//public Page<Product> getProductList(Pageable pageable, String search){
	public DataTablesOutput<Product> getProductList(DataTablesInput input){
	
		/*
		Specifications<Product> spec = Specifications.where(ProductSpecs.notBattle()).and(ProductSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(ProductSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(input);
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
	
	public List<CommonCode> getCategoryAll() {
		return codeRepo.getCategoryAll();
	}

}
//end of ProductService.java