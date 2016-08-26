package com.kmungu.api.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kmungu.api.user.domain.User;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
public class WebUtil {

	public static User getLoginUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static Long getLoginUserId() {
		User user = getLoginUser();
		return user.getId();
	}
	
	public static WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	}
	

}
//end of WebUtil.java