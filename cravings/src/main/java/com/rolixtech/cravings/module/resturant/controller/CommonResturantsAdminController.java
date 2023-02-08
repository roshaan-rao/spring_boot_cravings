package com.rolixtech.cravings.module.resturant.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.yaml.snakeyaml.util.ArrayUtils;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.services.CommonCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonResturantsAdminController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/admin/resturant";
	
	
	@Autowired
	private CommonResturantsService ResturantsService;
	

	@Autowired
	private GenericUtility utility;
    
    @PostMapping(CONTROLLER_URL+"/register")
	public ResponseEntity<?> Save(String recordId,Long userId,String label,String address,Long countryId,Long provinceId,Long cityId,Double lat,Double lng,Double accuracy,@RequestParam(name ="logo_img_url") MultipartFile logoImg,
			@RequestParam(name ="profile_img_url") MultipartFile profileImg ,@RequestParam(name ="banner_img_url") MultipartFile bannerImg,Long dayId[], @DateTimeFormat(pattern="HH:mm") Date openTimings[],@DateTimeFormat(pattern="HH:mm") Date closeTimings[],String contactNo,String email,Integer isActive,
			Integer isGst,Double gstPercentage,Double deliveryCharges,String contactNo2,String contactNo3,String contactNo4,@RequestHeader("authorization") String token)  { 
    		long AdminUserId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Saved";
				ResturantsService.saveResturantAdmin(Long.parseLong(recordId),userId.longValue(),label,address,(countryId).longValue(),(provinceId).longValue(),(cityId).longValue(),lat.doubleValue(),lng.doubleValue(),accuracy.doubleValue(),logoImg,profileImg,bannerImg,utility.toPrimitives(dayId),openTimings,closeTimings,contactNo,email,utility.parseInt(isActive),utility.parseInt(isGst),utility.parseDouble(gstPercentage),utility.parseDouble(deliveryCharges), contactNo2, contactNo3, contactNo4,AdminUserId);	
				
			
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
    
    
    @PostMapping(CONTROLLER_URL+"/view")
   	public ResponseEntity<?> Save(String recordId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsService.view(Long.parseLong(recordId));	
   				
   			
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
    
    
    
    @PutMapping(CONTROLLER_URL+"/changeStatus")
   	public ResponseEntity<?> changeStatus(String recordId,Integer status,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				ResturantsService.ChangeStatus(Long.parseLong(recordId),status.intValue(),UserId);	
   				
   			
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
   	public ResponseEntity<?> DeleteResturant(String recordId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				ResturantsService.deleteResturant(Long.parseLong(recordId),UserId);	
   				
   			
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
    
    
    @PutMapping(CONTROLLER_URL+"/changeActiveStatus")
   	public ResponseEntity<?> changeActiveStatus(String recordId,Integer isActive,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				ResturantsService.ChangeActiveStatus(Long.parseLong(recordId),isActive.intValue(),UserId);	
   				
   			
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