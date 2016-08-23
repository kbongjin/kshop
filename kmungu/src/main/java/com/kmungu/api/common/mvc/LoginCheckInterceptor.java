/**
 * 
 */
package com.kmungu.api.common.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kmungu.api.user.domain.Account;

/**
 * @author Administrator
 *
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Account loginUser = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return loginUser != null;
	}

	

}
