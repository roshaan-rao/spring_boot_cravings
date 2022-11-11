package com.rolixtech.cravings.module.auth.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
@RestController
@CrossOrigin()
public class AccessDeniedController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT ;
	
    
    @GetMapping(CONTROLLER_URL+"/access-denied")
    public ResponseEntity<?> accessDenied()  { 
		
		ResponseEntityOutput response=new ResponseEntityOutput();

		response.CODE="2";
		response.USER_MESSAGE="User Is Not Allowed, Access is Denied";
		response.SYSTEM_MESSAGE="HTTP CODE 403";
		response.DATA=new ArrayList<>();		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		
	}
    
    
    @GetMapping(CONTROLLER_URL+"/invalid-credentials")
    public ResponseEntity<?> invalidCredentials()  { 
		System.out.println("Yoooooooooooooooooooooooooooooooooooooooooo");
		ResponseEntityOutput response=new ResponseEntityOutput();

		response.CODE="2";
		response.USER_MESSAGE="User Is Not Allowed, Access is Denied";
		response.SYSTEM_MESSAGE="HTTP CODE 404";
		response.DATA=new ArrayList<>();		
		return ResponseEntity.ok(response);
		
	}
	 
	 
}