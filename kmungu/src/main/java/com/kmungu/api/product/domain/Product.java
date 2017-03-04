/**
 * 
 */
package com.kmungu.api.product.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.ManyToOne;
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
import com.kmungu.api.product.viewmodel.FileInputInitialPreviewConfig;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;//
	
	@Column(name = "name")
	private String name;//
	
	@Column(name = "product_master_id")
	private Integer productMasterId;//
	
	@Column(name = "retail_price")
	private int retailPrice;//소매가격(할인전 판매가격)
	
	@Column(name = "discount_price")
	private int discountPrice;//
	
	@Column(name = "selling_price")
	private int sellingPrice;//실제 판매 가격
	
	@Column(name = "stock_qty")
	private int stockQty; // 재고 수량.
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "update_dt")
	private Date updateDt;
	
	@Column(name = "update_user_id")
	private Long updateUserId;
	
	@Column(name = "hidden_reason")
	private String hiddenReason;
	
	@ManyToOne
	@JoinColumn(name = "product_master_id", insertable = false, updatable = false)
	private ProductMaster productMaster;
	

	public Product() {
		
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

	public Integer getProductMasterId() {
		return productMasterId;
	}

	public void setProductMasterId(Integer productMasterId) {
		this.productMasterId = productMasterId;
	}

	public ProductMaster getProductMaster() {
		return productMaster;
	}

	public void setProductMaster(ProductMaster productMaster) {
		this.productMaster = productMaster;
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

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
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
	
	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
		this.updateDt = this.createDt;
	}
}
