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
				.authorizeRequests().antMatchers(ALLOWED_URL1,ALLOWED_USER_URL2).permitAll().
				// all other requests need to be authenticated
						anyRequest().authenticated().and()
				// make sure we use stateless session; session won't be used to
				// store user's state.		
				.exceptionHandling().
//				authenticationEntryPoint(JwtAuthenticationEntryPoint).
//		        accessDeniedHandler(accessDeniedHandler()).
		        				
						and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}