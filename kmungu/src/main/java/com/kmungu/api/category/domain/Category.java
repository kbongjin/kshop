/**
 * 
 */
package com.kmungu.api.category.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "category")
@NamedEntityGraph(name = "Category.detail",
	attributeNodes = @NamedAttributeNode("children"))
public class Category {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//
	
	@Column(name = "name")
	private String name;//
	
	@Column(name = "level")
	private int level = 1;//
	
	@Column(name = "order_seq")
	private Integer orderSeq;//
	
	@Column(name = "parent_ctg_id")
	private Integer parentCtgId;//
	
	@Column(name = "view_cnt")
	private int viewCnt;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "parent_ctg_id", insertable = false, updatable = false)
	private Category parent;
	
	
	@OneToMany
	@JoinColumn(name = "parent_ctg_id", insertable = false, updatable = false)
	@OrderBy("orderSeq")
	private Set<Category> children;

	/**
	 * 
	 */
	public Category() {
		// TODO Auto-generated constructor stub
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
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the orderSeq
	 */
	public Integer getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	/**
	 * @return the parentCtgId
	 */
	public Integer getParentCtgId() {
		return parentCtgId;
	}

	/**
	 * @param parentCtgId the parentCtgId to set
	 */
	public void setParentCtgId(Integer parentCtgId) {
		this.parentCtgId = parentCtgId;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	@PrePersist
	@PreUpdate
	public void preInsert() {
		
		if (this.parentCtgId != null && parentCtgId == 0) {
			this.parentCtgId = null;
		}
	}
	
/*
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Category other = (Category) obj;
        
		return Objects.equal(this.id, other.id)
			&& Objects.equal(this.name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.level, this.name, this.orderSeq, this.parentCtgId);
	}
	*/

}
