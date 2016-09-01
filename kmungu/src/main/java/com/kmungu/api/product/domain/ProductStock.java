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
import javax.persistence.Transient;

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
	private int id;//
	
	@Column(name = "product_id")
	private int productId;//
	
	@Column(name = "unit_price")
	private int unitPrice;//단가(입고원가격)
	
	@Column(name = "qty")
	private String qty;//입고수량
	
	@Transient
	private int sumPrice;
	
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
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	public String getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

	public int getSumPrice() {
		return sumPrice;
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
	
	@PrePersist
	public void preInsert() {
		this.stockDt = new Date();
	}

}
