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
public class CommonResturantOrdersAdmin {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/admin/order";
	
	
	@Autowired
	private CustomerOrdersService OrdersService;
	
	@Autowired
	private CustomerOrderStatusService OrdersStatusService;
	
	@Autowired
	private GenericUtility utility;
	//consumes
	
    
    
    @RequestMapping(value=CONTROLLER_URL+"/active/view" )
   	public ResponseEntity<?> ActiveAllView(@RequestHeader("authorization") String token)  { 
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= OrdersService.getAllActiveOrders();
   			
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
    
    @RequestMapping(value=CONTROLLER_URL+"/active-order-summary/view" ) 
   	public ResponseEntity<?> ActiveAllViewDetail(Long recordId,@RequestHeader("authorization") String token)  { 
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= OrdersService.getActiveOrdersByOrderIdAdmin(utility.parseLong(recordId));
   			
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
    

    @GetMapping(value=CONTROLLER_URL+"/change-active-status/drop-down" )
	public ResponseEntity<?> statusDropDownView(@RequestHeader("authorization") String token)  { 
    	    long UserId=utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				

				response.CODE="1";
				response.USER_MESSAGE="Success";
				response.SYSTEM_MESSAGE="";
				response.DATA= OrdersStatusService.getAll();
			
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
    
    @GetMapping(value=CONTROLLER_URL+"/change-active/status" )
	public ResponseEntity<?> detailedView(@RequestHeader("authorization") String token)  { 
    	    long UserId=utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				

				response.CODE="1";
				response.USER_MESSAGE="Success";
				response.SYSTEM_MESSAGE="";
				response.DATA= OrdersService.getAllActiveOrders();
			
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