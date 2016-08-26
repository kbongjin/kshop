package com.kmungu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.kmungu.api.common.security.RefreshableRememberMeAuthenticationFilter;
import com.kmungu.api.user.UserService;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final static String REMEMBER_ME_KEY = "rem-me-key";

	@Autowired
	private UserService userService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/", 
				"/*.html", 
				"/resources/**", 
				"/WEB-INF/**", 

				
				"/auth/notLogin*", 
				"/auth/loginFail*",
				"/auth/accessDenied*", 
				"/auth/onAfterLogout*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.anonymous()
			.disable()
			.authorizeRequests()
			.expressionHandler(webExpressionHandler())
			
			.antMatchers("/user/locale", "/user/join").permitAll()
			.antMatchers("/account/*/resetPassword", "/account/*/changePassword").permitAll()
			.antMatchers(HttpMethod.GET, "/code/list/*").permitAll()
			
			.antMatchers("/dashboard").authenticated()

			.anyRequest()// other request
			.permitAll()
			//.authenticated()
			
			.and().exceptionHandling().accessDeniedPage("/auth/accessDenied")
			.and().formLogin()
				.usernameParameter("loginId")
				.passwordParameter("passwd")
				.loginPage("/auth/notLogin")
				//.loginPage("/page_402.html")
				.loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/auth/onAfterLogin", true)
				.failureUrl("/auth/loginFail")
			.and().logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/auth/onAfterLogout")
				
			//remember me configuration
			.and()
			.addFilter(rememberMeAuthenticationFilter())
			//.rememberMe()
                //.key(REMEMBER_ME_KEY)
                //.rememberMeParameter("remember-me-gstar")
                //.rememberMeCookieName("gstar-remember-me")
                //.tokenValiditySeconds(60 * 60 * 24)
                //.rememberMeServices(tokenBasedRememberMeServices())
			.csrf().disable()
			.setSharedObject(RememberMeServices.class, tokenBasedRememberMeServices());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(rememberMeAuthenticationProvider())
		.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * <pre>
	 * configure user password encoder
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		// return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl r = new RoleHierarchyImpl();
		r.setHierarchy("ROLE_ADMIN > ROLE_ADMIN_USER");
		return r;
	}

	private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler
				.setRoleHierarchy(roleHierarchy());
		return defaultWebSecurityExpressionHandler;
	}
	
	@Bean
	public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
		
		return new RefreshableRememberMeAuthenticationFilter(authenticationManager(), tokenBasedRememberMeServices());
	}
	
	@Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userService);
        //rememberMeServices.setAlwaysRemember(true);
        //rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24);//86400
        rememberMeServices.setCookieName("kmungu-remember-me");
        rememberMeServices.setParameter("remember-me-kmungu");
        return rememberMeServices;
    }
	
	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(REMEMBER_ME_KEY);
	}

}
//end of SecurityConfig.java