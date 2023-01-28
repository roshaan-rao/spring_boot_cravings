package com.rolixtech.cravings.module.auth.config;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rolixtech.cravings.module.generic.services.GenericUtility;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	
	public static final String ALLOWED_URL1 = GenericUtility.APPLICATION_CONTEXT + "/authentication";
	
	public static final String ALLOWED_USER_URL2 = GenericUtility.APPLICATION_CONTEXT + "/user/register";
	
	public static final String ALLOWED_Categories_URL1 = GenericUtility.APPLICATION_CONTEXT + "/categories/view";
	
	
	

	
	public static final String ALLOWED_GENERIC_URL1 = GenericUtility.APPLICATION_CONTEXT + "/generic/**";
	
	public static final String ALLOWED_CUSTOMER_URL1 = GenericUtility.APPLICATION_CONTEXT + "/customer/register";
	
	public static final String ALLOWED_CUSTOMER_URL2 = GenericUtility.APPLICATION_CONTEXT + "/customer/popular-resturants/view";
	
	public static final String ALLOWED_CUSTOMER_URL3 = GenericUtility.APPLICATION_CONTEXT + "/customer/resturants/search/view";
	
	public static final String ALLOWED_CUSTOMER_URL4 = GenericUtility.APPLICATION_CONTEXT + "/customer/resturants/view";
	
	
	public static final String ALLOWED_CUSTOMER_URL5 = GenericUtility.APPLICATION_CONTEXT + "/customer/resturants/products/view";

	public static final String ALLOWED_CUSTOMER_URL6 = GenericUtility.APPLICATION_CONTEXT + "/customer/resturants/product-single/view";
	
	public static final String ALLOWED_CUSTOMER_URL7 = GenericUtility.APPLICATION_CONTEXT + "/customer/promotional-banners/view";
	
	public static final String ALLOWED_CUSTOMER_URL8 = GenericUtility.APPLICATION_CONTEXT + "/customer/most-popular/products/view";
	
	public static final String ALLOWED_CUSTOMER_URL9 = GenericUtility.APPLICATION_CONTEXT + "/customer/resturants/common-categories-wise/view";
	
	public static final String ALLOWED_CUSTOMER_URL10 = GenericUtility.APPLICATION_CONTEXT + "/customer/getUserOTP";
	
	
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings("deprecation")
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
//	@Bean
//	public AccessDeniedHandler accessDeniedHandler() {
//		
//	   return new UnauthorizedEntryPoint();
//	}
	
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		
	   return new UnauthorizedEntryPoint();
	}
	
//	 @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return (request, response, authException) -> response.sendRedirect(GenericUtility.APPLICATION_CONTEXT+"/invalid-credentials");
//    }

//	 @Autowired
//	    @Qualifier("delegatedAuthenticationEntryPoint")
//	    AuthenticationEntryPoint authEntryPoint;
	 
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers(ALLOWED_URL1,ALLOWED_USER_URL2,ALLOWED_Categories_URL1,ALLOWED_GENERIC_URL1,ALLOWED_CUSTOMER_URL1,ALLOWED_CUSTOMER_URL2,ALLOWED_CUSTOMER_URL3,ALLOWED_CUSTOMER_URL4,ALLOWED_CUSTOMER_URL5,ALLOWED_CUSTOMER_URL6, ALLOWED_CUSTOMER_URL7,ALLOWED_CUSTOMER_URL8,ALLOWED_CUSTOMER_URL9,ALLOWED_CUSTOMER_URL10).permitAll().
				// all other requests need to be authenticated
						anyRequest().authenticated().and()
				// make sure we use stateless session; session won't be used to
				// store user's state.		
				.exceptionHandling().
				authenticationEntryPoint(JwtAuthenticationEntryPoint).
//		        accessDeniedHandler(accessDeniedHandler()).
		        				
						and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}