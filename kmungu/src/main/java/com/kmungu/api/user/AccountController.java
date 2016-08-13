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
import com.kmungu.api.user.domain.Account;
import com.kmungu.api.user.domain.PassresetToken;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private JavaMailSender mailSender;

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public AccountController() {
		
	}
	/*
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(){
	
		List list = service.getGstarAccountAllList();
		
		GridJsonResponse jsonRes = new GridJsonResponse();
		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}*/
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Account gstarAccount){
		
		service.save(gstarAccount);
		jsonRes.setMsg("사용자가 정상적으로 생성되었습니다.");
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId){
		
		service.deleteGstarAccount(accountId);
		jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarAccount(SimpleJsonResponse jsonRes, @PathVariable("accountId") Long accountId, Locale locale){
	
		Account account = service.getGstarAccount(accountId);
		
        if (account == null) {
            throw new AccountNotFoundException(accountId, locale);
        }
		
		jsonRes.setData(account);
		
		return jsonRes;
	}
	
	@RequestMapping(value = "/{accountId}/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public SimpleJsonResponse resetPassword(SimpleJsonResponse jsonRes, HttpServletRequest request, Locale locale,
    		@PathVariable("accountId") Long accountId, @RequestParam("email") String userEmail) {
		
		Account account = service.getGstarAccount(accountId);
		
        if (account == null) {
            throw new AccountNotFoundException(accountId, locale);
        }
        
        if (account.getUser().getEmail().equals(userEmail) == false) {
        	jsonRes.setSuccess(false);
        	jsonRes.setMsg(messageSource.getMessage("account.email.not.reg", new String[]{userEmail}, locale));
        	
        	return jsonRes;
		}
        
        String token = service.createPasswordResetToken(account);
        
        mailSender.send(constructResetTokenEmail(getAppUrl(request), locale, accountId, token, userEmail));
        LOGGER.info("email sended to {}", userEmail);
        
        return jsonRes;
    }
	
	private SimpleMailMessage constructResetTokenEmail(final String appUrl, final Locale locale, Long accountId, String token, String toEmail) {
        final String url = appUrl + "/account/"+accountId+"/changePassword?token=" + token;
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
    
    @RequestMapping(value = "/{accountId}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Locale locale, Model model, @PathVariable("accountId") Long accountId, @RequestParam("token") String token) {
         
    	PassresetToken passToken = service.getPasswordResetToken(token);
        Account user = null;
        
        if (passToken != null) {
        	user = passToken.getAccount();
		}
        
        Calendar cal = Calendar.getInstance();
        
        String invalidMsg = null;
        if (passToken == null || user.getId() != accountId) {
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
        Account user = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        service.changeUserPassword(user, password);
        
        jsonRes.setMsg(messageSource.getMessage("message.update.success.password", null, locale));
        
        return jsonRes;
    }

}
//end of GstarAccountController.java