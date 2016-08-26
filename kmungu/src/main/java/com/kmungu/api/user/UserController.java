package com.kmungu.api.user;


import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmungu.api.common.exception.AccountNotFoundException;
import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.user.domain.PassresetToken;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserService service;
	
	@Autowired
    private JavaMailSender mailSender;

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
	public SimpleJsonResponse join(SimpleJsonResponse jsonRes, User user, Locale locale){
		
		
		UserDetails userDetails = service.loadUserByUsername(user.getLoginId());
		
		if (userDetails != null) {
			jsonRes.setSuccess(false);
			jsonRes.setMsg(messageSource.getMessage("user.loginId.alreadyUse", new String[]{user.getLoginId()}, locale));
			
			return jsonRes;
		}
		
		service.save(user);
		
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
	
		User account = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		jsonRes.setData(account);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/my", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveMyInfo(SimpleJsonResponse jsonRes, @RequestParam("id") Long userId, User user, Locale locale){
	
		User account = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (account.getId() != user.getId()) {
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
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse resetPassword(SimpleJsonResponse jsonRes, HttpServletRequest request, Locale locale,
    		@RequestParam("loginId") String loginId, @RequestParam("email") String userEmail) {
		
		User user = (User)service.loadUserByUsername(loginId);
		
        if (user == null) {
            throw new AccountNotFoundException(loginId, locale);
        }
        
        if (user.getEmail().equals(userEmail) == false) {
        	jsonRes.setSuccess(false);
        	jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
        	
        	return jsonRes;
		}
        
        String token = service.createPasswordResetToken(user);
        
        mailSender.send(constructResetTokenEmail(getAppUrl(request), locale, user.getId(), token, userEmail));
        LOGGER.info("email sended to {}", userEmail);
        
        return jsonRes;
    }
	
	private SimpleMailMessage constructResetTokenEmail(final String appUrl, final Locale locale, Long userId, String token, String toEmail) {
        final String url = appUrl + "/account/"+userId+"/changePassword?token=" + token;
        final String message = messageSource.getMessage("account.email.resetPassword.msg", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, toEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String toEmail) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(toEmail);
        email.setFrom("idkbj@osci.kr");
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    
    @RequestMapping(value = "/{userId}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Locale locale, Model model, @PathVariable("userId") Long userId, @RequestParam("token") String token) {
         
    	PassresetToken passToken = service.getPasswordResetToken(token);
        User user = null;
        
        if (passToken != null) {
        	user = passToken.getUser();
		}
        
        Calendar cal = Calendar.getInstance();
        
        String invalidMsg = null;
        if (passToken == null || user.getId() != userId) {
        	invalidMsg = messageSource.getMessage("auth.invalidToken", null, locale);
            
        } else if ((passToken.getExpireDt().getTime() - cal.getTime().getTime()) <= 0) {
        	invalidMsg = messageSource.getMessage("auth.expiredToken", null, locale);
        }
     
        if (invalidMsg == null) {
        	UserDetails userDetail = service.loadUserByUsername(user.getLoginId());
            
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            
		} else {
			model.addAttribute("message", invalidMsg);
		}
     
        //return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        return "ChangePassword";
    }
    
    @RequestMapping(value = "/save/password", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse savePassword(SimpleJsonResponse jsonRes, Locale locale, @RequestParam("password") String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        service.changeUserPassword(user, password);
        
        jsonRes.setMsg(messageSource.getMessage("message.update.success.password", null, locale));
        
        return jsonRes;
    }

}
//end of UserController.java