package com.kmungu.api.user;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.user.domain.Account;
import com.kmungu.api.user.domain.User;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private AccountService accountService;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	/*
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<user> list = service.getuserAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}
	*/
	/**
	 * <pre>
	 * 회원 가입.
	 * </pre>
	 * @param jsonRes
	 * @param user
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/join", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse join(SimpleJsonResponse jsonRes, User user, Account account, Locale locale){
		
		
		UserDetails gstarAccount = accountService.loadUserByUsername(account.getLoginId());
		
		if (gstarAccount != null) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("user.loginId.alreadyUse", new String[]{account.getLoginId()}, locale));
			
			return jsonRes;
		}
		
		service.join(user, account);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, User user){
		
		service.save(user);
		//jsonRes.setMsg(" 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("userId") Long userId){
		
		service.deleteUser(userId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	

	@RequestMapping(value="/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getUser(SimpleJsonResponse jsonRes, @PathVariable("userId") Long userId){
	
		jsonRes.setData(service.getUser(userId));
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getMyInfo(SimpleJsonResponse jsonRes){
	
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		account.getUser().getId();//lazy loading.
		
		jsonRes.setData(account.getUser());
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveMyInfo(SimpleJsonResponse jsonRes, @RequestParam("id") Long userId, User user, Locale locale){
	
		Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (account.getUser().getId() != user.getId()) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("user.my.update.denied", null, locale));
		} else {
			service.save(user);
		}
		
		return jsonRes;
	}
	
	@RequestMapping(value="/locale", method = RequestMethod.PUT)
	@ResponseBody
	public SimpleJsonResponse chageLocale(SimpleJsonResponse jsonRes, Locale locale){
	
		jsonRes.setMsg(messageSource.getMessage("user.locale.change.success", null, locale));
		
		return jsonRes;
	}

}
//end of UserController.java