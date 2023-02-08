package com.rolixtech.cravings.module.users.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonUsersCustomerController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/customer";
	
	
	@Autowired
	private CommonUsersService UsersService;
	 
	@Autowired
	private GenericUtility Utility; 
	
    @PostMapping(CONTROLLER_URL+"/register")
	public ResponseEntity<?> Register(String name,String mobile,String email,String password,String confirmPassword )  { 
    		
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Saved";
				response.SYSTEM_MESSAGE="";
				UsersService.registerNewCustomerUserAccount( name, email, mobile, password,confirmPassword);
			
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
				
			}
			
			return ResponseEntity.ok(response);
	}
    
    
    
    
    @PostMapping(CONTROLLER_URL+"/view")
	public ResponseEntity<?> View(String recordId,@RequestHeader("authorization") String token)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=UsersService.findAllUsers(Long.parseLong(recordId));
			
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
				
			}
			
			
			return ResponseEntity.ok(response);
	}
    
    
    @GetMapping(CONTROLLER_URL+"/resturantRole/view")
	public ResponseEntity<?> ViewNewUsers(@RequestHeader("authorization") String token)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Success";
				response.SYSTEM_MESSAGE="";
				response.DATA=UsersService.findAllUsersWithResturantRole();
			
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
				
			}
			
			
			return ResponseEntity.ok(response);
	}
    
    
    @DeleteMapping(CONTROLLER_URL+"/delete") 
	public ResponseEntity<?> Delete(String recordId,@RequestHeader("authorization") String token)  { 
    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Deleted";
				response.SYSTEM_MESSAGE="";
				UsersService.deleteUser(Long.parseLong(recordId),UserID);
			
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
				
			}
			
			
			return ResponseEntity.ok(response);
	}
    
    @PostMapping(CONTROLLER_URL+"/setNewPassword")
	public ResponseEntity<?> setNewPassword(String newPassword,@RequestHeader("authorization") String token)  { 
    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			try {
				response.CODE="1";
				response.USER_MESSAGE="Your Password has been changed.";
				response.SYSTEM_MESSAGE="Success";
				UsersService.setNewPassword(UserID, newPassword);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
			}
			return ResponseEntity.ok(response);
	}
    
    
    @PostMapping(CONTROLLER_URL+"/setForgotPassword")
	public ResponseEntity<?> FogotPassword(String mobile, String newPassword)  { 
//    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			try {
				response.CODE="1";
				response.USER_MESSAGE="Your Password has been changed.";
				response.SYSTEM_MESSAGE="Success";
				UsersService.setForgotPassword(mobile, newPassword);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
			}
			return ResponseEntity.ok(response);
	}
    
    
    @GetMapping(CONTROLLER_URL+"/UserProfileView")
	public ResponseEntity<?> UserProfileView(@RequestHeader("authorization") String token)  { 
    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			try {
				response.CODE="1";
				response.USER_MESSAGE="Success";
				response.SYSTEM_MESSAGE="Success";
				response.DATA=UsersService.userProfile(UserID);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
			}
			return ResponseEntity.ok(response);
	}
    
    
    @GetMapping(CONTROLLER_URL+"/getUserOTP")
	public ResponseEntity<?> GetUserOTP(String phoneno)  { 
//    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			try {
				response.CODE="1";
				response.USER_MESSAGE="Success";
				response.SYSTEM_MESSAGE="Success";
				response.DATA=UsersService.sendOTP(phoneno);
				
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE=e.getMessage();
				response.SYSTEM_MESSAGE=e.toString();
			}
			return ResponseEntity.ok(response);
	}
    
}