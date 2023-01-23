package com.rolixtech.cravings.module.resturant.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOn;
import com.rolixtech.cravings.module.resturant.services.CommonCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsGroupTypesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonResturantsOwnerProductsController {
   
	
	
	
	@Autowired
	private CommonResturantsProductsService ResturantsProductsService;
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private CommonResturantsProductsGroupTypesService ProductsGroupTypesService;
	
	@Autowired
	private GenericUtility utility;
    
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/resturant-owner/products";
	
	
	 /*********************************** resturant-products  Starts **********************************/
	
    @PostMapping(CONTROLLER_URL+"/register")
	public ResponseEntity<?> Save(long recordId,String label,@RequestParam(required = false) String description,Long resturantId,@RequestParam(required = false) Double salesTax,@RequestParam(required = false) Double salesTaxPercentage,@RequestParam(required = false) Double grossAmount,@RequestParam(required = false) Double netAmount,@RequestParam(required = false) Double discount,@RequestParam(name ="productImgUrl",required = false) MultipartFile productImgUrl,
			@RequestParam(required = false) Double rate,@RequestParam(required = false) Integer isTimingEnable,@RequestParam(required = false) @DateTimeFormat(pattern="HH:mm") Date availabilityFrom,@RequestParam(required = false) @DateTimeFormat(pattern="HH:mm") Date availabilityTo,@RequestParam(required = false) Integer isAvailable,@RequestParam(required = false) Long resturantCategoryId,@RequestParam(required = false) Integer isExtra,@RequestHeader("authorization") String token)  { 
    		long resturantOwnerId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {	
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Saved";
				
				
				ResturantsProductsService.saveProduct(recordId, label, description, utility.parseLong(resturantId), utility.parseDouble(salesTax), utility.parseDouble(salesTaxPercentage),utility.parseDouble( grossAmount),utility.parseDouble(netAmount), utility.parseDouble(discount),utility.parseDouble(rate), utility.parseInt(isTimingEnable), availabilityFrom, availabilityTo,utility.parseInt(isAvailable), productImgUrl,utility.parseLong(resturantCategoryId),utility.parseInt(isExtra));
						
					
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
    
    
   
    @PostMapping(CONTROLLER_URL+"/add-on/save")
	public ResponseEntity<?> addOnSave(Long recordId,Long productId,String groupTitle,Long groupTypeId,Long selectionTypeId,@RequestParam(required = false) Integer minSelection,@RequestParam(required = false)  Integer maxSelection, Long[] addOnProductId,Double[] price ,@RequestHeader("authorization") String token)  { 
    		long resturantOwnerId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {	
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Saved";
				
				
				ResturantsProductsService.saveAddOnProduct(utility.parseLong(recordId), utility.parseLong(productId), groupTitle, utility.parseLong(groupTypeId),utility.parseLong(selectionTypeId),utility.parseInt(minSelection),utility.parseInt(maxSelection),  utility.parseLong(addOnProductId), utility.parseDouble(price));
						
					
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
    
    @PostMapping(CONTROLLER_URL+"/add-on/view")
   	public ResponseEntity<?> viewAddOns(Long recordId,Long resturantId, @RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.viewAddOnProducts(utility.parseLong(recordId),utility.parseLong(resturantId));	
   				
   			
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
    
    
    @DeleteMapping(CONTROLLER_URL+"/add-on/delete")
   	public ResponseEntity<?> deleteAddOns(Long recordId, @RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				ResturantsProductsService.deleteAddOn(utility.parseLong(recordId));	
   				
   			
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
   
    
    
    
    @PostMapping(CONTROLLER_URL+"/add-on/drop-down/view")
   	public ResponseEntity<?> viewProductsForAddOn(Long resturantId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.addOnDropDownView(resturantId.longValue());	
   				
   			
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
    
    
    @PostMapping(CONTROLLER_URL+"/group-types/view")
   	public ResponseEntity<?> viewExtras(@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.addOnProductsGroupsTypesDropDownView();	
   				
   			
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
   	public ResponseEntity<?> Save(long recordId,Long resturantId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.view(recordId,resturantId.longValue());	
   				
   			
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

    
    @DeleteMapping(CONTROLLER_URL+"/delete")
   	public ResponseEntity<?> DeleteResturant(String recordId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				ResturantsProductsService.deleteProduct(Long.parseLong(recordId),UserId);	
   				
   			
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
    
    
    @PostMapping(CONTROLLER_URL+"/drop-down/view")
   	public ResponseEntity<?> View(Long resturantId,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.dropDownView(resturantId.longValue());	
   				
   			
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
    
    
    @PutMapping(CONTROLLER_URL+"/changeActiveStatus")
   	public ResponseEntity<?> changeActiveStatus(String recordId,Integer isActive,@RequestHeader("authorization") String token)  { 
       		long UserId=utility.getUserIDByToken(token);
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Updated";
   				ResturantsProductsService.ChangeActiveStatus(Long.parseLong(recordId),isActive.intValue(),UserId);	
   				
   			
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
    
   
    
    /*********************************** resturant-products  Ends **********************************/
    
	 
}