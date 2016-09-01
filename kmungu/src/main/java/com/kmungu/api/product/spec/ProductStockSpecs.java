/**
 * 
 */
package com.kmungu.api.product.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kmungu.api.product.domain.ProductStock;

/**
 * @author Administrator
 *
 */
public class ProductStockSpecs {

	public static Specification<ProductStock> eqProductId(final Integer productId) {
		
		return new Specification<ProductStock> () {

			@Override
			public Predicate toPredicate(Root<ProductStock> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.equal(root.get("productId"), productId);
			}
			
		};
	}
}
