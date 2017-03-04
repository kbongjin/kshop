package com.kmungu.api.category;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.category.domain.Category;
import com.kmungu.api.common.model.SimpleJsonResponse;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public CategoryController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String getListPage(Model model, @PageableDefault(sort = { "orderSeq" }, direction = Direction.ASC, size = 100) Pageable pageable, 
			String search, Integer parentCtgId){
	
		Page<Category> list = service.getCategoryList(pageable, search, parentCtgId);

		model.addAttribute("paging", list);
		
		if (parentCtgId == null) {
			return "categoryTabs";
		}
		
		return "categoryList";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<Category> getList(@PageableDefault(sort = { "orderSeq" }, direction = Direction.ASC, size = 100) Pageable pageable, 
			String search, Integer parentCtgId){
	
		Page<Category> list = service.getCategoryList(pageable, search, parentCtgId);

		return list;
	}
	
	@RequestMapping(value = "/topNav/json", method = RequestMethod.GET)
	@ResponseBody
	public Set<Category> getTopNavJson(){
	
		Set<Category> list = service.findTopNav();

		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Category category){
		
		service.save(category);
		//jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
		jsonRes.setData(category);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{categoryId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("categoryId") Integer categoryId){
		
		service.deleteCategory(categoryId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{categoryId}", method = RequestMethod.GET)
	public String getCategory(Model model, @PathVariable("categoryId") Integer categoryId, Category category){
	
		if (categoryId > 0) {
			category = service.getCategory(categoryId);
		}
		
		model.addAttribute("category", category);
		
		return "categoryForm";
	}

}
//end of CategoryController.java