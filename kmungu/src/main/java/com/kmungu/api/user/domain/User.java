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
 * BongJin Kwon		2016. 7. 15.		First Draft.
 */
package com.kmungu.api.user.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Entity
@Table(name = "user")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //http://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
public class User implements Serializable {

	private static final long serialVersionUID = 7382139896085707035L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;//
	
	@Column(name = "name")
	private String name;//사용자명
	
	@Column(name = "email")
	private String email;//
	
	@Column(name = "gender_cd")
	private String genderCd;//성별코드 ( 1: 남자, 2: 여자)
	
	@Column(name = "age")
	private short age;//
	
	
	@Column(name = "create_dt", updatable = false)
	private java.util.Date createDt;//생성일시
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Account> accounts;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public User() {
	}
	
	public User(Long id) {
		super();
		this.id = id;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the genderCd
	 */
	public String getGenderCd() {
		return genderCd;
	}

	/**
	 * @param genderCd the genderCd to set
	 */
	public void setGenderCd(String genderCd) {
		this.genderCd = genderCd;
	}

	/**
	 * @return the age
	 */
	public short getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(short age) {
		this.age = age;
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
	
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
//end of GstarUser.java