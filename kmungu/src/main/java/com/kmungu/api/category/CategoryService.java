package com.kmungu.api.category;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kmungu.api.category.domain.Category;
import com.kmungu.api.category.domain.CategoryRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository repository;
	
	public CategoryService() {
	}
	
	@CacheEvict(cacheNames="topNav", allEntries=true)
	public void save(Category category){
		repository.save(category);
	}
	
	public List<Category> getCategoryAllList(){
		return repository.findAll();
	}
	
	public Page<Category> getCategoryList(Pageable pageable, String search, Integer parentCtgId){
	
		/*
		Specifications<Category> spec = Specifications.where(CategorySpecs.notBattle()).and(CategorySpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(CategorySpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		if (parentCtgId == null) {
			return repository.findByLevel(1, pageable);
		}
		
		return repository.findByParentCtgId(parentCtgId, pageable);
	}
	
	@Cacheable("topNav")
	public Set<Category> findTopNav() {
		//return repository.findTopNav();
		return repository.findByLevelOrderByOrderSeq(1);
	}
	
	public Category getCategory(Integer categoryId){
		return repository.findOne(categoryId);
	}
	
	/*
	public void updateCategory(Category category){
		repository.updateCategory(category);
	}
	*/
	
	@CacheEvict(cacheNames="topNav", allEntries=true)
	public void deleteCategory(Integer categoryId){
		repository.delete(categoryId);
	}

}
//end of CategoryService.java