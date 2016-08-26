/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 8. 1.		First Draft.
 */
package com.kmungu.api.user.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "passreset_token")
public class PassresetToken implements Serializable {

	private static final long serialVersionUID = -8995962578792426679L;

	@Id
	@Column(name = "user_id")
	private Long userId;//
	
	@Column(name = "token")
	private String token;//
	
	@Column(name = "expire_dt")
	private java.util.Date expireDt;//
	
	@Column(name = "create_dt")
	private java.util.Date createDt;//
	
	@OneToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	

	public PassresetToken() {
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public PassresetToken(Long userId, String token, Date expiredDt) {
		this.userId = userId;
		this.token = token;
		this.expireDt = expiredDt;
	}


	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the expireDt
	 */
	public java.util.Date getExpireDt() {
		return expireDt;
	}

	/**
	 * @param expireDt the expireDt to set
	 */
	public void setExpireDt(java.util.Date expireDt) {
		this.expireDt = expireDt;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PrePersist
	@PreUpdate
	public void preInsert() {
		this.createDt = new Date();
	}

}
//end of GstarPassresetToken.java