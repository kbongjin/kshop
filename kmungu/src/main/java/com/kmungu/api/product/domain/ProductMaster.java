/**
 * 
 */
package com.kmungu.api.product.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kmungu.api.category.domain.Category;
import com.kmungu.api.common.converter.JsonDateSerializer;
import com.kmungu.api.common.util.JSONUtil;
import com.kmungu.api.common.util.WebUtil;
import com.kmungu.api.product.ProductController;
import com.kmungu.api.product.ProductMasterController;
import com.kmungu.api.product.viewmodel.FileInputInitialPreviewConfig;

/**
 * @author BongJin Kwon
 *
 */
@Entity
@Table(name = "product_master")
public class ProductMaster {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "name")
	private String name;//
	
	@Column(name = "maker")
	private String maker;//
	
	@Column(name = "retail_price")
	private int retailPrice;//
	
	@Column(name = "discount_rate")
	private Float discountRate;//
	
	@Column(name = "discount_price")
	private int discountPrice;//
	
	@Column(name = "selling_price")
	private int sellingPrice;//
	
	@Column(name = "contents")
	private String contents;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "update_dt")
	private java.util.Date updateDt;//
	
	@Column(name = "update_user_id")
	private Long updateUserId;//
	
	@Column(name = "hidden_reson")
	private String hiddenReson;//
	
	@Column(name = "related_product_master_id")
	private Integer relatedProductMasterId;//
	
	@Column(name = "view_cnt")
	private int viewCnt;
	
	@OneToMany(mappedBy = "productMaster")
	private List<Product> products;
	
	@Transient
	private String imgUri;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "product_master_id", insertable = false, updatable = false)
	private List<ProductImg> productImgs;
	
	@ManyToMany
	@JoinTable(name = "category_product", joinColumns = @JoinColumn(name = "product_master_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;
	
	@JsonIgnore
	@Transient
	private List<String> initialPreviews = new ArrayList<String>();
	
	@JsonIgnore
	@Transient
	private List<FileInputInitialPreviewConfig> initialPreviewConfigs = new ArrayList<FileInputInitialPreviewConfig>();
	
	@JsonIgnore
	@Transient
	private String initialPreview;
	
	@JsonIgnore
	@Transient
	private String initialPreviewConfig;

	/**
	 * 
	 */
	public ProductMaster() {
		// 
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the maker
	 */
	public String getMaker() {
		return maker;
	}

	/**
	 * @param maker the maker to set
	 */
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	/**
	 * @return the img1Path
	 */
	public String getImg1Path() {
		
		if (productImgs != null && productImgs.size() > 0) {
			return getImgUri() + productImgs.get(0).getImgPath();
		}
		
		return null;
	}

	/**
	 * @return the retailPrice
	 */
	public int getRetailPrice() {
		return retailPrice;
	}

	/**
	 * @param retailPrice the retailPrice to set
	 */
	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}

	/**
	 * @return the discountRate
	 */
	public Float getDiscountRate() {
		return discountRate;
	}

	/**
	 * @param discountRate the discountRate to set
	 */
	public void setDiscountRate(Float discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * @return the discountPrice
	 */
	public int getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * @return the sellingPrice
	 */
	public int getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the createDt
	 */
	public java.util.Date getCreateDt() {
		return createDt;
	}

	/**
	 * @param createDt the createDt to set
	 */
	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}

	/**
	 * @return the updateDt
	 */
	public java.util.Date getUpdateDt() {
		return updateDt;
	}

	/**
	 * @param updateDt the updateDt to set
	 */
	public void setUpdateDt(java.util.Date updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * @return the updateUserId
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the hiddenReson
	 */
	public String getHiddenReson() {
		return hiddenReson;
	}

	/**
	 * @param hiddenReson the hiddenReson to set
	 */
	public void setHiddenReson(String hiddenReson) {
		this.hiddenReson = hiddenReson;
	}

	/**
	 * @return the relatedProductMasterId
	 */
	public Integer getRelatedProductMasterId() {
		return relatedProductMasterId;
	}

	/**
	 * @param relatedProductMasterId the relatedProductMasterId to set
	 */
	public void setRelatedProductMasterId(Integer relatedProductMasterId) {
		this.relatedProductMasterId = relatedProductMasterId;
	}
	
	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getImgUri() {
		return ProductMasterController.IMG_URI;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public List<ProductImg> getProductImgs() {
		return productImgs;
	}

	public void setProductImgs(List<ProductImg> productImgs) {
		this.productImgs = productImgs;
	}

	public List<String> getInitialPreviews() {
		
		for (ProductImg pImg : this.productImgs) {
			initialPreviews.add(getImgUri() + pImg.getImgPath());
		}
		
		return initialPreviews;
	}

	public List<FileInputInitialPreviewConfig> getInitialPreviewConfigs() {
		
		for (ProductImg pImg : this.productImgs) {
			initialPreviewConfigs.add(new FileInputInitialPreviewConfig(pImg));
		}
		
		return initialPreviewConfigs;
	}

	public String getInitialPreview() {
		try{
			return JSONUtil.objToJson(getInitialPreviews());
		}catch (IOException e) {
			return null;
		}
	}

	public String getInitialPreviewConfig() {
		
		try {
			return JSONUtil.objToJson(getInitialPreviewConfigs());
		}catch (IOException e) {
			return null;
		}
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
		this.updateDt = this.createDt;
		this.updateUserId = WebUtil.getLoginUserId();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateDt = new Date();
		this.updateUserId = WebUtil.getLoginUserId();
	}
}
