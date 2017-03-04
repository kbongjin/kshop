/**
 * 
 */
package com.kmungu.api.product.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kmungu.api.product.domain.ProductMaster;

/**
 * @author Administrator
 *
 */
public class ProductMasterSpecs {
	public static Specification<ProductMaster> likeCtg1(final String ctg1) {
		
		return new Specification<ProductMaster>() {

			@Override
			public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.like(root.<String>get("categoryCd"), ctg1 +'%');
			}
			
		};
	}
	
	public static Specification<ProductMaster> eqCtg2(final String ctg2) {
		
		return new Specification<ProductMaster>() {

			@Override
			public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<String>get("categoryCd"), ctg2 );
			}
			
		};
	}
}
