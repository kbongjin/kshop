/**
 * 
 */
package com.kmungu.api.category.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author BongJin Kwon
 *
 */
@Entity
@Table(name = "category_product")
public class CategoryProduct {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "category_id")
	private int categoryId;//
	
	@Column(name = "product_master_id")
	private Integer productMasterId;//

	/**
	 * 
	 */
	public CategoryProduct() {
		
	}

	public CategoryProduct(int categoryId, Integer productMasterId) {
		super();
		this.categoryId = categoryId;
		this.productMasterId = productMasterId;
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
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getProductMasterId() {
		return productMasterId;
	}

	public void setProductMasterId(Integer productMasterId) {
		this.productMasterId = productMasterId;
	}

}
