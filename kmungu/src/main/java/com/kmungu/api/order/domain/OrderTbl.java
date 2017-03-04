/**
 * 
 */
package com.kmungu.api.order.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kmungu.api.common.converter.JsonDateSerializer;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "order_tbl")
public class OrderTbl {
	
	@Id
	@Column(name = "order_no")
	private String orderNo;//주문번호
	
	@Column(name = "user_id")
	private Long userId;//
	
	@Column(name = "order_price")
	private int orderPrice;//총 주문금액
	
	@Column(name = "status_cd")
	private String statusCd;//
	
	@Column(name = "pg_id")
	private String pgId;//결제ID
	
	@Column(name = "parcle_inv_no")
	private String parcleInvNo;//택배운송장번호
	
	@Column(name = "parcle_inv_price")
	private Integer parcleInvPrice;//
	
	@Column(name = "recv_name")
	private String recvName;//
	
	@Column(name = "recv_post_no")
	private String recvPostNo;//
	
	@Column(name = "recv_addr1")
	private String recvAddr1;//
	
	@Column(name = "recv_addr2")
	private String recvAddr2;//
	
	@Column(name = "recv_phone_no")
	private String recvPhoneNo;//
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "order_dt", updatable = false)
	private Date orderDt;// 주문일시.

	/**
	 * 
	 */
	public OrderTbl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the orderPrice
	 */
	public int getOrderPrice() {
		return orderPrice;
	}

	/**
	 * @param orderPrice the orderPrice to set
	 */
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * @param statusCd the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the pgId
	 */
	public String getPgId() {
		return pgId;
	}

	/**
	 * @param pgId the pgId to set
	 */
	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	/**
	 * @return the parcleInvNo
	 */
	public String getParcleInvNo() {
		return parcleInvNo;
	}

	/**
	 * @param parcleInvNo the parcleInvNo to set
	 */
	public void setParcleInvNo(String parcleInvNo) {
		this.parcleInvNo = parcleInvNo;
	}

	/**
	 * @return the parcleInvPrice
	 */
	public Integer getParcleInvPrice() {
		return parcleInvPrice;
	}

	/**
	 * @param parcleInvPrice the parcleInvPrice to set
	 */
	public void setParcleInvPrice(Integer parcleInvPrice) {
		this.parcleInvPrice = parcleInvPrice;
	}

	/**
	 * @return the recvName
	 */
	public String getRecvName() {
		return recvName;
	}

	/**
	 * @param recvName the recvName to set
	 */
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}

	/**
	 * @return the recvPostNo
	 */
	public String getRecvPostNo() {
		return recvPostNo;
	}

	/**
	 * @param recvPostNo the recvPostNo to set
	 */
	public void setRecvPostNo(String recvPostNo) {
		this.recvPostNo = recvPostNo;
	}

	/**
	 * @return the recvAddr1
	 */
	public String getRecvAddr1() {
		return recvAddr1;
	}

	/**
	 * @param recvAddr1 the recvAddr1 to set
	 */
	public void setRecvAddr1(String recvAddr1) {
		this.recvAddr1 = recvAddr1;
	}

	/**
	 * @return the recvAddr2
	 */
	public String getRecvAddr2() {
		return recvAddr2;
	}

	/**
	 * @param recvAddr2 the recvAddr2 to set
	 */
	public void setRecvAddr2(String recvAddr2) {
		this.recvAddr2 = recvAddr2;
	}

	/**
	 * @return the recvPhoneNo
	 */
	public String getRecvPhoneNo() {
		return recvPhoneNo;
	}

	/**
	 * @param recvPhoneNo the recvPhoneNo to set
	 */
	public void setRecvPhoneNo(String recvPhoneNo) {
		this.recvPhoneNo = recvPhoneNo;
	}

	public Date getOrderDt() {
		return orderDt;
	}

	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}
	
	@PrePersist
	public void preInsert() {
		this.orderDt = new Date();
	}

}
