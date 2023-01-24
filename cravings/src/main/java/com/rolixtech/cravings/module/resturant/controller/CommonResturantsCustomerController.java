package com.rolixtech.cravings.module.resturant.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.services.CustomerOrdersService;
import com.rolixtech.cravings.module.resturant.services.CommonCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.resturant.services.CommonUsersFavProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonUsersResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonResturantsCustomerController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/customer";
	
	
	@Autowired
	private CommonResturantsService ResturantsService;
	
	@Autowired
	private CommonUsersFavProductsService FavProductsService;
	
	
	
	
	@Autowired
	private CommonResturantsProductsService ResturantsProductsService;
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private CustomerOrdersService OrdersService;

	@Autowired
	private CommonUsersResturantsService UsersResturantsService;

	
	@Autowired
	private GenericUtility utility;
    
    @PostMapping(CONTROLLER_URL+"/popular-resturants/view")
	public ResponseEntity<?> View(String latitude,String longitude,int limit)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=ResturantsService.getAllPopularResturantsByLatLngLimit(Double.parseDouble(latitude), Double.parseDouble(longitude), limit);
			
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
    
    
    @PostMapping(CONTROLLER_URL+"/resturants/search/view")
   	public ResponseEntity<?> View(String keyword,int searchType)  { 
   			
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="";
   				response.DATA=ResturantsService.getAllByResturantNameORProductsName(keyword,searchType);
   			
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
    
    @PostMapping(CONTROLLER_URL+"/resturants/view")
   	public ResponseEntity<?> ViewOneOrAll(String resturantId)  { 
       		
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsService.viewCompleteResturant(Long.parseLong(resturantId));	
   				
   			
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
    
    
    
    @PostMapping(CONTROLLER_URL+"/resturant-categories/view")
   	public ResponseEntity<?> View(Long resturantId)  { 
   			
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
    
    
    @PostMapping(CONTROLLER_URL+"/resturant-categories/products/view")
   	public ResponseEntity<?> Save(long recordId,Long resturantId, Long resturantCategoryId)  { 
       		
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.viewCategoryWiseProducts(recordId,resturantId.longValue(), resturantCategoryId.longValue());	
   				
   			
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
    
    @PostMapping(CONTROLLER_URL+"/fav-products/save")
   	public ResponseEntity<?> Save(Long productId,@RequestHeader("authorization") String token)  { 
    	long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Saved";
   				FavProductsService.addProductToUsersFav(productId.longValue(),UserId);	
   				
   			
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

      
    @PostMapping(CONTROLLER_URL+"/fav-products/view")
   	public ResponseEntity<?> ViewUserFav(@RequestHeader("authorization") String token)  { 
    	long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=FavProductsService.viewAllFavProductToUsersFav(UserId);	
   				
   			
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
    
    @PostMapping(CONTROLLER_URL+"/resturants/product-single/view")
   	public ResponseEntity<?> viewSingleProduct(Long productId)  { 
       		
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=ResturantsProductsService.viewSingleProduct(utility.parseLong(productId));	
   				
   			
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
    
    @PostMapping(CONTROLLER_URL+"/most-popular/products/view")
   	public ResponseEntity<?> viewMostPopularOrder(int limit)  { 
       		
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=OrdersService.getMostPopularProducts(limit);	
   				
   			
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
    
    @PostMapping(CONTROLLER_URL+"/resturants/prmotional-banners/view")
   	public ResponseEntity<?> viewPromotionalBanners()  { 
       		
       	
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				
   				response.CODE="1";
   				response.USER_MESSAGE="";
   				response.SYSTEM_MESSAGE="Success";
   				response.DATA=UsersResturantsService.bannersView();	
   				
   			
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
    
	 
	 
}