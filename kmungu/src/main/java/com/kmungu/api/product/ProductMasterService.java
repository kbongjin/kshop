package com.kmungu.api.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kmungu.api.category.domain.CategoryProduct;
import com.kmungu.api.category.domain.CategoryProductRepository;
import com.kmungu.api.product.domain.Product;
import com.kmungu.api.product.domain.ProductImg;
import com.kmungu.api.product.domain.ProductMaster;
import com.kmungu.api.product.domain.ProductMasterRepository;
import com.kmungu.api.product.domain.ProductRepository;
import com.kmungu.api.product.spec.ProductMasterSpecs;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class ProductMasterService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ProductMasterService.class);
	private static final String[] IMG_DIRS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};

	@Autowired
	protected ProductMasterRepository repository;
	
	@Value("${km.upload.location}")
	private String uploadPath;
	
	@Autowired
	private ProductImgService imgService;
	
	@Autowired
	private CategoryProductRepository ctgProductRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	public ProductMasterService() {
		// TODO Auto-generated constructor stub
	}
	

	@Transactional
	public void saveWithImages(ProductMaster productMaster, MultipartFile[] imgFiles, Integer[] ctgIds){
		Integer originPid = productMaster.getId();
		
		productMaster.setDiscountPrice(productMaster.getRetailPrice() - productMaster.getSellingPrice());
		productMaster = repository.save(productMaster);
		
		try{
			for (int i = 0; i < imgFiles.length; i++) {
				File uploadedFile = new File(uploadPath + imgFiles[i].getOriginalFilename());
				imgFiles[i].transferTo(uploadedFile);
	
				imgService.save(new ProductImg(productMaster.getId(), imgFiles[i].getOriginalFilename(), imgFiles[i].getSize()));
			}
			
			if (ctgIds != null) {
				
				if (originPid != null && originPid > 0) {
					ctgProductRepo.deleteByProductMasterId(originPid);
				}
				
				for (Integer ctgId : ctgIds) {
					CategoryProduct ctgProduct = new CategoryProduct(ctgId, productMaster.getId());
					
					ctgProductRepo.save(ctgProduct);
				}
			}
			
			if (productMaster.getDiscountRate() > 0) {
				
				//productMaster = repository.findOne(productMaster.getId());
				entityManager.refresh(productMaster);// if don't do this then produts is null.
				
				List<Product> products = productMaster.getProducts();
				
				if (products != null && products.size() > 0) {
					
					for (Product product : products) {
						
						product.setSellingPrice(getSellingPrice(product.getRetailPrice(), productMaster.getDiscountRate()));
						product.setDiscountPrice(product.getRetailPrice() - product.getSellingPrice());
						
						productRepo.save(product);
					}
				}
				
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int getSellingPrice(int retailPrice, float discountRate) {
		
		int sellingPrice = (int)Math.ceil( retailPrice * ((100 - discountRate) / 100D) );
		
		LOGGER.debug("sellingPrice = {}", sellingPrice);
		
		return sellingPrice;
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
	
	public void save(ProductMaster productMaster){
		repository.save(productMaster);
	}
	
	public DataTablesOutput<ProductMaster> getProductMasterList(DataTablesInput input, String ctg1, String ctg2){
	
		Specifications<ProductMaster> spec = null;
		if (StringUtils.isEmpty(ctg2) == false) {
			spec = Specifications.where(ProductMasterSpecs.eqCtg2(ctg2));
		} else if (StringUtils.isEmpty(ctg1) == false) {
			spec = Specifications.where(ProductMasterSpecs.likeCtg1(ctg1));
		}
		
		if (spec != null) {
			return repository.findAll(input, spec);
		}
		
		return repository.findAll(input);
	}
	
	/*
	public int getProductMasterListTotalCount(GridParam gridParam){
		
		return repository.getProductMasterListTotalCount(gridParam);
	}
	*/
	
	public ProductMaster getProductMaster(Integer productMasterId){
		return repository.findOne(productMasterId);
	}
	
	/*
	public void updateProductMaster(ProductMaster productMaster){
		repository.updateProductMaster(productMaster);
	}
	*/
	
	public void deleteProductMaster(Integer productMasterId){
		
		ProductMaster p = getProductMaster(productMasterId);

		if (p.getProductImgs() != null) {
			for (ProductImg pImg : p.getProductImgs()) {
				FileUtils.deleteQuietly(new File(uploadPath + pImg.getImgPath()));
			}
		}
		
		repository.delete(productMasterId);
	}

}
//end of ProductMasterService.java