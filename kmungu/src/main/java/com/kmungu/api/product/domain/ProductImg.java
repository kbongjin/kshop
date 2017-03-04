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

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "product_img")
public class ProductImg {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int key;//
	
	@Column(name = "product_master_id")
	private Integer productMasterId;//
	
	@Column(name = "img_path")
	private String imgPath;//
	
	@Column(name = "file_size")
	private Long fileSize;//
	
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//

	/**
	 * 
	 */
	public ProductImg() {
		// TODO Auto-generated constructor stub
	}

	public ProductImg(Integer productMasterId, String imgPath, Long fileSize) {
		super();
		this.productMasterId = productMasterId;
		this.imgPath = imgPath;
		this.fileSize = fileSize;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	public Integer getProductMasterId() {
		return productMasterId;
	}

	public void setProductMasterId(Integer productMasterId) {
		this.productMasterId = productMasterId;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
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
