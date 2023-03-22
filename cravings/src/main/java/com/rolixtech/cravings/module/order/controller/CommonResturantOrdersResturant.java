package com.rolixtech.cravings.module.order.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
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
import com.rolixtech.cravings.module.order.POJO.OrderAddressPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsOptionalAddOnPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsRequiredAddOnPOJO;
import com.rolixtech.cravings.module.order.services.CustomerOrderStatusService;
import com.rolixtech.cravings.module.order.services.CustomerOrdersService;
import com.rolixtech.cravings.module.resturant.services.CommonCategoriesService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
@RestController
@CrossOrigin()
public class CommonResturantOrdersResturant {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/resturant-owner/order";
	
	
	@Autowired
	private CustomerOrdersService OrdersService;
	
	@Autowired
	private CustomerOrderStatusService OrdersStatusService;
	
	@Autowired
	private GenericUtility utility;
	//consumes
	
	 @PostMapping(value=CONTROLLER_URL+"/status-wise-active/view" )
	 public ResponseEntity<?> ActiveAllView(Long resturantId,Long orderStatusId,@RequestHeader("authorization") String token)  { 
	       	    long UserId=utility.getUserIDByToken(token);
	   			ResponseEntityOutput response=new ResponseEntityOutput();
	   			Map map=new HashMap<>();
	   			
	   			try {
	   				

	   				response.CODE="1";
	   				response.USER_MESSAGE="Success";
	   				response.SYSTEM_MESSAGE="";
	   				response.DATA= OrdersService.getAllActiveOrdersByResturantId(utility.parseLong(resturantId),utility.parseLong(orderStatusId));
	   			
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
	   
	 @PostMapping(value=CONTROLLER_URL+"/change-status" )
		public ResponseEntity<?> detailedView(long orderId,long statusId,@RequestHeader("authorization") String token)  { 
	    	    long UserId=utility.getUserIDByToken(token);
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					

					response.CODE="1";
					response.USER_MESSAGE="Updated";
					response.SYSTEM_MESSAGE="";
					OrdersService.changeOrderStatusByResturant(orderId,statusId,UserId);
				
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
	 
	 
	 
	 @PostMapping(value=CONTROLLER_URL+"/get-help" )
		public ResponseEntity<?> getHelp(long orderId,@RequestHeader("authorization") String token)  { 
	    	    long UserId=utility.getUserIDByToken(token);
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					

					response.CODE="1";
					response.USER_MESSAGE="Updated";
					response.SYSTEM_MESSAGE="";
					OrdersService.getHelp(orderId);
				
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