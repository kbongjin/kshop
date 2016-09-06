/**
 * 
 */
package com.kmungu.api.product.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kmungu.api.product.domain.Product;

/**
 * @author Administrator
 *
 */
public class ProductSpecs {
	public static Specification<Product> likeCtg1(final String ctg1) {
		
		return new Specification<Product>() {

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.like(root.<String>get("categoryCd"), ctg1 +'%');
			}
			
		};
	}
	
	public static Specification<Product> eqCtg2(final String ctg2) {
		
		return new Specification<Product>() {

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.equal(root.<String>get("categoryCd"), ctg2 );
			}
			
		};
	}
}
