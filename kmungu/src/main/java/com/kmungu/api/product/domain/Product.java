/**
 * 
 */
package com.kmungu.api.product.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

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
	
	@Column(name = "img1_path")
	private String img1Path;//
	
	@Column(name = "retail_price")
	private int retailPrice;//소매가격(할인전 판매가격)
	
	@Column(name = "discount_rate")
	private Float discountRate;//
	
	@Column(name = "discount_price")
	private int discountPrice;//
	
	@Column(name = "selling_price")
	private int sellingPrice;//실제 판매 가격
	
	@Column(name = "img2_path")
	private String img2Path;//
	
	@Column(name = "img3_path")
	private String img3Path;//
	
	@Column(name = "contents")
	private String contents;//
	
	@Column(name = "category_cd")
	private String categoryCd;//
	
	@Column(name = "create_dt")
	private java.util.Date createDt;//


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

	/**
	 * @return the img1Path
	 */
	public String getImg1Path() {
		return img1Path;
	}

	/**
	 * @param img1Path the img1Path to set
	 */
	public void setImg1Path(String img1Path) {
		this.img1Path = img1Path;
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
	 * @return the img2Path
	 */
	public String getImg2Path() {
		return img2Path;
	}

	/**
	 * @param img2Path the img2Path to set
	 */
	public void setImg2Path(String img2Path) {
		this.img2Path = img2Path;
	}

	/**
	 * @return the img3Path
	 */
	public String getImg3Path() {
		return img3Path;
	}

	/**
	 * @param img3Path the img3Path to set
	 */
	public void setImg3Path(String img3Path) {
		this.img3Path = img3Path;
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
	 * @return the categoryCd
	 */
	public String getCategoryCd() {
		return categoryCd;
	}

	/**
	 * @param categoryCd the categoryCd to set
	 */
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
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
	
	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
