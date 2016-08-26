/**
 * 
 */
package com.kmungu.api.user.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "user")
public class User implements UserDetails{

	private static final long serialVersionUID = -7327265341863624098L;

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
	
	@Column(name = "login_id")
	private String loginId;//
	
	@Column(name = "passwd")
	private String passwd;//
	
	@Column(name = "enabled")
	private boolean enabled;//
	
	@Column(name = "locked")
	private boolean locked;//
	
	@Column(name = "last_login_dt")
	private java.util.Date lastLoginDt;//
	
	@Column(name = "post_no")
	private String postNo;//
	
	@Column(name = "addr1")
	private String addr1;//
	
	@Column(name = "addr2")
	private String addr2;//
	
	@Column(name = "phone_no")
	private String phoneNo;//
	
	@Column(name = "create_dt")
	private java.util.Date createDt;//생성일시
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private List<AccountAuth> accountAuths;

	/**
	 * 
	 */
	public User() {
		
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
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the lastLoginDt
	 */
	public java.util.Date getLastLoginDt() {
		return lastLoginDt;
	}

	/**
	 * @param lastLoginDt the lastLoginDt to set
	 */
	public void setLastLoginDt(java.util.Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	/**
	 * @return the postNo
	 */
	public String getPostNo() {
		return postNo;
	}

	/**
	 * @param postNo the postNo to set
	 */
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	/**
	 * @return the addr1
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * @param addr1 the addr1 to set
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	/**
	 * @return the addr2
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * @param addr2 the addr2 to set
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public List<AccountAuth> getAccountAuths() {
		return accountAuths;
	}

	public void setAccountAuths(List<AccountAuth> accountAuths) {
		this.accountAuths = accountAuths;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		if (this.accountAuths != null) {
			for (AccountAuth auth : this.accountAuths) {
				if (auth != null) {
					authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
				}
			}
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		
		return this.passwd;
	}

	@Override
	public String getUsername() {
		return this.loginId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@PrePersist
	public void preInsert() {
		this.createDt = new Date();
	}

}
