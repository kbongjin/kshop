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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kmungu.api.common.converter.JsonDateSerializer;
import com.kmungu.api.common.util.WebUtil;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "product_stock")
public class ProductStock {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "product_id")
	private int productId;//
	
	@Column(name = "unit_price")
	private int unitPrice;//단가(입고원가격)
	
	@Column(name = "qty")
	private int qty;//입고수량
	
	@Transient
	private int sumPrice;// unitPrice * qty
	
	@Column(name = "stock_qty")
	private int stockQty;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "update_dt")
	private Date updateDt;
	
	@Column(name = "update_user_id")
	private Long updateUserId;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "stock_dt", updatable = false)
	private java.util.Date stockDt;//입고일시

	/**
	 * 
	 */
	public ProductStock() {
		
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
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the unitPrice
	 */
	public int getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getSumPrice() {
		return qty * unitPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	/**
	 * @return the stockDt
	 */
	public java.util.Date getStockDt() {
		return stockDt;
	}

	/**
	 * @param stockDt the stockDt to set
	 */
	public void setStockDt(java.util.Date stockDt) {
		this.stockDt = stockDt;
	}
	
	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
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
		this.stockDt = new Date();
		this.updateDt = this.stockDt;
		this.updateUserId = WebUtil.getLoginUserId();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateDt = new Date();
		this.updateUserId = WebUtil.getLoginUserId();
	}

}
