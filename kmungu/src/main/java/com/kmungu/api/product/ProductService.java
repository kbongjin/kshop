package com.kmungu.api.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kmungu.api.code.domain.CommonCode;
import com.kmungu.api.code.domain.CommonCodeRepository;
import com.kmungu.api.product.domain.Product;
import com.kmungu.api.product.domain.ProductImg;
import com.kmungu.api.product.domain.ProductRepository;
import com.kmungu.api.product.domain.ProductStock;
import com.kmungu.api.product.spec.ProductSpecs;
import com.kmungu.api.product.spec.ProductStockSpecs;


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
	private static final String[] IMG_DIRS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};

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
	
	public String saveProductContentsImage(MultipartFile imgFile) {
		
		Random random = new Random();
		
		String fileName = IMG_DIRS[random.nextInt(IMG_DIRS.length)] + "/" + imgFile.getOriginalFilename();
		
		File uploadedFile = new File(uploadPath + fileName);
		
		try{
			if (uploadedFile.getParentFile().exists() == false) {
				uploadedFile.getParentFile().mkdirs();
			}
			
			imgFile.transferTo(uploadedFile);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}
	
	public List<Product> getProductAllList(){
		return (List)repository.findAll();
	}
	
	//public Page<Product> getProductList(Pageable pageable, String search){
	public DataTablesOutput<Product> getProductList(DataTablesInput input, String ctg1, String ctg2){
	
		Specifications<Product> spec = null;
		if (StringUtils.isEmpty(ctg2) == false) {
			spec = Specifications.where(ProductSpecs.eqCtg2(ctg2));
		} else if (StringUtils.isEmpty(ctg1) == false) {
			spec = Specifications.where(ProductSpecs.likeCtg1(ctg1));
		}
		
		if (spec != null) {
			return repository.findAll(input, spec);
		}
		
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