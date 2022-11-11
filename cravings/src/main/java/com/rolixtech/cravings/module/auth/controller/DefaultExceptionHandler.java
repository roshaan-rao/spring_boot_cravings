package com.rolixtech.cravings.module.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rolixtech.cravings.module.auth.model.JwtResponse;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;

@ControllerAdvice
public class DefaultExceptionHandler {
 
    @ExceptionHandler
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody ResponseEntityOutput
    handleException(Exception ex)
    {
    	System.out.println("ex"+ex);
    	List<Map> resultRes=new ArrayList<Map>();
        return new ResponseEntityOutput("2", ex+"",ex.getMessage()+"", ex+"",resultRes);
    }
}