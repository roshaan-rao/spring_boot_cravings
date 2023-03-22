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
import com.rolixtech.cravings.module.order.services.CustomerOrderStatusService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import com.rolixtech.cravings.module.users.services.CommonUsersStatusesService;
@RestController
@CrossOrigin()
public class CommonUsersAdminController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/admin/user";
	
	
	@Autowired
	private CommonUsersService UsersService;
	
	@Autowired
	private CommonUsersStatusesService StatusesService;
	 
	@Autowired
	private GenericUtility Utility; 
	
	
	
 /*************************************************WEB Internal Calls Start********************************************************/
	    
	
    @PostMapping(CONTROLLER_URL+"/internal-user/register")
	public ResponseEntity<?> RegisterInternalUser(String recordId,String firstName,String lastName,String mobile,String email,String password,String completeAddress,
			Long countryId,Long provinceId,Long cityId,String postalCode,@RequestParam(name ="profileImage",required=false) MultipartFile profileImage,long roleId,@RequestHeader("authorization") String token)  { 
    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Saved";
				response.SYSTEM_MESSAGE="";
				UsersService.registerNewInternalWebAccount(Long.parseLong(recordId), firstName, lastName, email, mobile, password,completeAddress, countryId, provinceId, cityId, postalCode,profileImage,roleId);
			
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
    
    @PostMapping(CONTROLLER_URL+"/internal-user/view")
	public ResponseEntity<?> ViewInternal(String recordId,@RequestHeader("authorization") String token)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=UsersService.findAllInternalUsers(Long.parseLong(recordId));
			
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
    
    
    
    @DeleteMapping(CONTROLLER_URL+"/internal-user/delete") 
	public ResponseEntity<?> DeleteInternal(String recordId,@RequestHeader("authorization") String token)  { 
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
    
    /*************************************************WEB Internal Calls Start********************************************************/
	 
    
	  
	   /*************************************************WEB Customer Calls Start********************************************************/
	    
	
    @PostMapping(CONTROLLER_URL+"/customer/register")
	public ResponseEntity<?> RegisterCustomer(String recordId,String firstName,String lastName,String mobile,String email,String password,String completeAddress,
			Long countryId,Long provinceId,Long cityId,String postalCode,@RequestParam(name ="profileImage",required=false) MultipartFile profileImage,@RequestHeader("authorization") String token)  { 
    		long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Saved";
				response.SYSTEM_MESSAGE="";
				UsersService.registerNewCustomerWebAccount(Long.parseLong(recordId), firstName, lastName, email, mobile, password,completeAddress, countryId, provinceId, cityId, postalCode,profileImage);
			
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
    
    @PostMapping(CONTROLLER_URL+"/customer/view")
	public ResponseEntity<?> ViewCustomer(String recordId,@RequestHeader("authorization") String token)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=UsersService.findAllCustomerUsers(Long.parseLong(recordId));
			
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
    
    
    
    @DeleteMapping(CONTROLLER_URL+"/customer/delete") 
	public ResponseEntity<?> DeleteCustomer(String recordId,@RequestHeader("authorization") String token)  { 
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
    
    /*************************************************WEB Customer Calls Start********************************************************/
	 
    
    
    @PostMapping(CONTROLLER_URL+"/resturant/register")
   	public ResponseEntity<?> RegisterResturant(String recordId,String firstName,String lastName,String mobile,String email,String password,String completeAddress,
   			Long countryId,Long provinceId,Long cityId,String postalCode,@RequestParam(name ="profileImage",required=false) MultipartFile profileImage,@RequestHeader("authorization") String token)  { 
       		long UserID =Utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="Saved";
   				response.SYSTEM_MESSAGE="";
   				UsersService.registerNewResturantAccount(Long.parseLong(recordId), firstName, lastName, email, mobile, password,completeAddress, countryId, provinceId, cityId, postalCode,profileImage);
   			
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
    
    @PostMapping(CONTROLLER_URL+"/resturant/view")
   	public ResponseEntity<?> ViewResturant(String recordId,@RequestHeader("authorization") String token)  { 
   			
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="";
   				response.DATA=UsersService.findAllResturantUsers(Long.parseLong(recordId));
   			
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
    
    
    @DeleteMapping(CONTROLLER_URL+"/resturant/delete") 
   	public ResponseEntity<?> DeleteResturant(String recordId,@RequestHeader("authorization") String token)  { 
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
    
    
    
    
    
    
    
    @GetMapping(value=CONTROLLER_URL+"/existing-customers/drop-down" )
   	public ResponseEntity<?> customersDropDownView( @RequestHeader("authorization") String token)  { 
       	   
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= UsersService.getAllUsers();
   			
   			}

   			catch (Exception e) {
   				// TODO: handle exception
   				e.printStackTrace();
   				response.CODE="2";
   				response.USER_MESSAGE="Error";
   				response.SYSTEM_MESSAGE=e.toString();
   				
   			}
   			
   			
   			return ResponseEntity.ok(response);
   	}
    
    @PostMapping(value=CONTROLLER_URL+"/existing-customer/seacrh" )
   	public ResponseEntity<?> customeDetailView(String mobile,Long userId, @RequestHeader("authorization") String token)  { 
       	   
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= UsersService.findAllByUserIdOrMobile(Utility.parseLong(userId),mobile);
   			
   			}

   			catch (Exception e) {
   				// TODO: handle exception
   				e.printStackTrace();
   				response.CODE="2";
   				response.USER_MESSAGE="Error";
   				response.SYSTEM_MESSAGE=e.toString();
   				
   			}
   			
   			
   			return ResponseEntity.ok(response);
   	}
    
    
    
   /*************************************************Rider Calls Start********************************************************/
    
    @PostMapping(CONTROLLER_URL+"/register-rider")
   	public ResponseEntity<?> RegisterRider(String recordId,String firstName,String lastName,String mobile,String email,String password,String completeAddress,String cnic, String licenseNumber,String userName,@RequestParam(name ="cnicImage",required=false) MultipartFile cnicImage, @RequestParam(name ="licenseNumberImage",required=false) MultipartFile licenseNumberImage,@RequestParam(name ="utilityBillImage",required=false) MultipartFile utilityBillImage,@RequestParam(name ="profileImage",required=false) MultipartFile profileImage,@RequestHeader("authorization") String token)  { 
       		long UserID =Utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="Saved";
   				response.SYSTEM_MESSAGE="";
   				UsersService.registerNewRiderUserAccount(Long.parseLong(recordId), firstName, lastName, email, mobile, password,completeAddress,profileImage,cnic,licenseNumber,cnicImage,licenseNumberImage,utilityBillImage,userName);
   			
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
       
    
    
    @PostMapping(CONTROLLER_URL+"/view-rider")
	public ResponseEntity<?> ViewRider(String recordId,@RequestHeader("authorization") String token)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=UsersService.findAllRiders(Long.parseLong(recordId));
			
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
    
    
    
    @DeleteMapping(CONTROLLER_URL+"/delete-rider") 
   	public ResponseEntity<?> DeleteRider(String recordId,@RequestHeader("authorization") String token)  { 
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
    
    
    @GetMapping(value=CONTROLLER_URL+"/change-status/drop-down" )
   	public ResponseEntity<?> statusDropDownView(@RequestHeader("authorization") String token)  { 
       	   
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= StatusesService.getAll();
   			
   			}

   			catch (Exception e) {
   				// TODO: handle exception
   				e.printStackTrace();
   				response.CODE="2";
   				response.USER_MESSAGE="Error";
   				response.SYSTEM_MESSAGE=e.toString();
   				
   			}
   			
   			
   			return ResponseEntity.ok(response);
   	}
    
    
    
    @PutMapping(CONTROLLER_URL+"/change-status")
   	public ResponseEntity<?> changeStatus(Long recordId,Integer statusId,@RequestHeader("authorization") String token)  { 
       		long UserId=Utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="Updated"; 
   				response.SYSTEM_MESSAGE="Updated";
   				UsersService.ChangeStatus(Utility.parseLong(recordId),Utility.parseInt(statusId),UserId);	
   				
   			
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
    /*************************************************Rider Calls Ends********************************************************/
    
	 
	 
}