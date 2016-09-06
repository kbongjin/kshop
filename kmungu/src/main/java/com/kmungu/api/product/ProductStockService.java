package com.kmungu.api.product;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.kmungu.api.product.domain.ProductRepository;
import com.kmungu.api.product.domain.ProductStock;
import com.kmungu.api.product.domain.ProductStockRepository;
import com.kmungu.api.product.spec.ProductStockSpecs;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class ProductStockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductStockService.class);

	@Autowired
	private ProductStockRepository repository;
	
	@Autowired
	private ProductRepository productRepo;
	
	public ProductStockService() {
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	public void save(ProductStock productStock){
		repository.save(productStock);
		
		productRepo.updateProductStockQty(productStock.getProductId());
	}
	
	public List<ProductStock> getProductStockAllList(){
		return (List)repository.findAll();
	}
	
	public DataTablesOutput<ProductStock> getProductStockList(DataTablesInput input, Integer productId){
	
		
		Specifications<ProductStock> spec = Specifications.where(ProductStockSpecs.eqProductId(productId));
		
		return repository.findAll(input, spec);
	}
	
	
	public ProductStock getProductStock(Integer productStockId){
		return repository.findOne(productStockId);
	}
	
	/*
	public void updateProductStock(ProductStock productStock){
		repository.updateProductStock(productStock);
	}
	*/
	
	@Transactional
	public void deleteProductStock(Integer productStockId){
		
		ProductStock stock = getProductStock(productStockId);
		
		repository.delete(productStockId);
		
		productRepo.updateProductStockQty(stock.getProductId());
	}

}
//end of ProductStockService.java