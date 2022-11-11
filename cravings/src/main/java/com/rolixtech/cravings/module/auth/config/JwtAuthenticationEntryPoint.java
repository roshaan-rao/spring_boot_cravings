package com.rolixtech.cravings.module.auth.config;


import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.rolixtech.cravings.module.generic.services.GenericUtility;



import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint,AuthenticationFailureHandler,Serializable{

	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT ;
    private static final long serialVersionUID = 3798723588865329956L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		 response.sendRedirect(CONTROLLER_URL+"/invalid-credentials");
		
	}
}



//@Component("delegatedAuthenticationEntryPoint")
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Autowired
//    @Qualifier("handlerExceptionResolver")
//    private HandlerExceptionResolver resolver;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
//      throws IOException, ServletException {
//        resolver.resolveException(request, response, null, authException);
//    }
//}
