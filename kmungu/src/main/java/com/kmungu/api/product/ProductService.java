package com.kmungu.api.product;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kmungu.api.code.domain.CommonCode;
import com.kmungu.api.code.domain.CommonCodeRepository;
import com.kmungu.api.product.domain.Product;
import com.kmungu.api.product.domain.ProductImg;
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
	
	@Autowired
	private ProductImgService imgService;
	
	@Value("${km.upload.location}")
	private String uploadPath;
	
	public ProductService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(Product product){
		repository.save(product);
	}
	
	@Transactional
	public void saveWithImages(Product product, MultipartFile[] imgFiles){
		repository.save(product);
		
		try{
			for (int i = 0; i < imgFiles.length; i++) {
				File uploadedFile = new File(uploadPath + imgFiles[i].getOriginalFilename());
				imgFiles[i].transferTo(uploadedFile);
	
				imgService.save(new ProductImg(product.getId(), imgFiles[i].getOriginalFilename(), imgFiles[i].getSize()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		
		Product p = getProduct(productId);
		
		if (p.getProductImgs() != null) {
			for (ProductImg pImg : p.getProductImgs()) {
				FileUtils.deleteQuietly(new File(uploadPath + pImg.getImgPath()));
			}
		}
		
		repository.delete(productId);
	}
	
	public List<CommonCode> getCategoryAll() {
		return codeRepo.getCategoryAll();
	}

}
//end of ProductService.java