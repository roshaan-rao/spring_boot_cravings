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

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.services.CommonCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonResturantsOwnerController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/resturant-owner";
	
	
	@Autowired
	private CommonResturantsService ResturantsService;
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private GenericUtility utility;
    
    @PostMapping(CONTROLLER_URL+"/register")
	public ResponseEntity<?> Save(String recordId,String label,String address,Long countryId,Long provinceId,Long cityId,Double lat,Double lng,Double accuracy,@RequestParam(name ="logo_img_url") MultipartFile logoImg,
			@RequestParam(name ="profile_img_url") MultipartFile profileImg ,@RequestParam(name ="banner_img_url") MultipartFile bannerImg,Long dayId[], @DateTimeFormat(pattern="HH:mm") Date openTimings[],@DateTimeFormat(pattern="HH:mm") Date closeTimings[],String contactNo,String email,@RequestHeader("authorization") String token)  { 
    		long resturantOwnerId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Saved";
				ResturantsService.saveResturantOwner(Long.parseLong(recordId),label,address,(countryId).longValue(),(provinceId).longValue(),(cityId).longValue(),lat.doubleValue(),lng.doubleValue(),accuracy.doubleValue(),logoImg,profileImg,bannerImg,resturantOwnerId,utility.toPrimitives(dayId),openTimings,closeTimings,contactNo,email);	
					
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
    
    
   
    
   
    
    
    
    @GetMapping(CONTROLLER_URL+"/view")
   	public ResponseEntity<?> Save(@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsService.viewResturant(UserId);	
   				
   			
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
   	public ResponseEntity<?> changeStatus(String recordId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				//response.DATA=ResturantsService.C(Long.parseLong(recordId));	
   				
   			
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
    
   
    /*********************************** resturant-categories Start **********************************/
    @PostMapping(CONTROLLER_URL+"/resturant-categories/view")
   	public ResponseEntity<?> View(long recordId,Long resturantId,@RequestHeader("authorization") String token)  { 
   			
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="";
   				response.DATA=ResturantsCategoriesService.getAll(recordId,resturantId);
   			
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
    
   
    @PostMapping(CONTROLLER_URL+"/resturant-categories/register")
   	public ResponseEntity<?> Save(long recordId,String label,Long resturantId,Long categoryId,@RequestHeader("authorization") String token)  { 
    	long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="";
   				ResturantsCategoriesService.SaveResturantsCategories(recordId,label,resturantId.longValue(),categoryId.longValue(),UserId);
   			
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
    
     
    @PostMapping(CONTROLLER_URL+"/resturant-categories/update")
   	public ResponseEntity<?> changeStatus(long recordId,int isActive,@RequestHeader("authorization") String token)  { 
    	long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="Udpated";
   				response.SYSTEM_MESSAGE="";
   				ResturantsCategoriesService.changeResturantCategoryStatus(recordId,isActive,UserId);
   			
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
    
    
    @DeleteMapping(CONTROLLER_URL+"/resturant-categories/delete")
   	public ResponseEntity<?> delete(long recordId,@RequestHeader("authorization") String token)  { 
    	long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="Deleted";
   				response.SYSTEM_MESSAGE="";
   				ResturantsCategoriesService.deleteResturantCategory(recordId,UserId);
   			
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
    
    
    @PostMapping(CONTROLLER_URL+"/resturant-categories/dropdown/view")
   	public ResponseEntity<?> View(Long resturantId,@RequestHeader("authorization") String token)  { 
   			
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="";
   				response.DATA=ResturantsCategoriesService.getAllDropDown(resturantId);
   			
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
	 
    
    /*********************************** resturant-categories Ends **********************************/
    
    
	 
}