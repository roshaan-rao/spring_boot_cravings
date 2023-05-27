package com.rolixtech.cravings.module.order.controller;
import java.util.*;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
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

	@RequestMapping(value=CONTROLLER_URL+"/save",consumes ="application/json" )
	public ResponseEntity<?> Save(@RequestBody OrderPOJO order, @RequestHeader("authorization") String token)  {
		long UserId=utility.getUserIDByToken(token);
		ResponseEntityOutput response=new ResponseEntityOutput();
		Map map=new HashMap<>();

		try {


			response.CODE="1";
			response.USER_MESSAGE="Order has been placed Successfully";
			response.SYSTEM_MESSAGE="";
			OrdersService.SaveAdmin(order,UserId);

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
    
    @PostMapping(value=CONTROLLER_URL+"/active/view" )
   	public ResponseEntity<?> ActiveAllView(@RequestParam(name="orderStatusIds",required = false)  Long[] orderStatusIds, @RequestHeader("authorization") String token)  { 
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= OrdersService.getAllActiveOrders(orderStatusIds,UserId);
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
    
    @GetMapping(value=CONTROLLER_URL+"/all-orders/view" )
   	public ResponseEntity<?> ActiveOrdersView(@RequestHeader("authorization") String token,String startDate, String endDate)  {
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
//   				response.DATA= OrdersService.getAllOrders();
				response.DATA= OrdersService.getAllOrders(UserId,startDate,endDate);
   			
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
    
    @PostMapping(value=CONTROLLER_URL+"/active-order-summary/view" ) 
   	public ResponseEntity<?> ActiveAllViewDetail(Long recordId,short dayId, @RequestHeader("authorization") String token)  {
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				response.CODE="1";
   				response.USER_MESSAGE="Success";
   				response.SYSTEM_MESSAGE="";
   				response.DATA= OrdersService.getActiveOrdersByOrderIdAdmin(recordId, dayId);
   			
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
				response.DATA= OrdersStatusService.getAllAdmin();
			
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
    
    @PostMapping(value=CONTROLLER_URL+"/change-active/status" )
	public ResponseEntity<?> detailedView(long recordId,long statusId,int remainingTime, @RequestHeader("authorization") String token)  {
    	    long UserId=utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {

				response.CODE="1";
				response.USER_MESSAGE="Updated";
				response.SYSTEM_MESSAGE="";
				OrdersService.changeOrderStatus(recordId,statusId,remainingTime,UserId);
			
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
    
    @PostMapping(value=CONTROLLER_URL+"/add-comments" )
   	public ResponseEntity<?> addComments(long recordId,String cravingsRemarks,@RequestHeader("authorization") String token)  { 
       	    long UserId=utility.getUserIDByToken(token);
   			ResponseEntityOutput response=new ResponseEntityOutput();
   			Map map=new HashMap<>();
   			
   			try {
   				

   				response.CODE="1";
   				response.USER_MESSAGE="Updated";
   				response.SYSTEM_MESSAGE="";
   				OrdersService.addCravingsComments(recordId,cravingsRemarks,UserId);
   			
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

	@PostMapping(value=CONTROLLER_URL+"/acknowledged-assign" )
	public ResponseEntity<?> assignAcknowledge(@RequestParam(name="orderId",required = false)  Long orderId, @RequestHeader("authorization") String token, boolean flag)  {
		long userId=utility.getUserIDByToken(token);
		ResponseEntityOutput response=new ResponseEntityOutput();
		Map map=new HashMap<>();

		try {
			response.CODE="1";
			response.USER_MESSAGE="Success";
			response.SYSTEM_MESSAGE="";
			response.DATA= OrdersService.assignAcknowledgedBy(orderId,userId,flag);

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

	@GetMapping(value=CONTROLLER_URL+"/get-orders-total-count" )
	public ResponseEntity<?> getOrdersTotalCount(@RequestHeader("authorization") String token)  {
		long UserId=utility.getUserIDByToken(token);
		ResponseEntityOutput response=new ResponseEntityOutput();
		Map map=new HashMap<>();

		try {
			response.CODE="1";
			response.USER_MESSAGE="Success";
			response.SYSTEM_MESSAGE="";
			response.DATA= OrdersStatusService.getOrdersCount();

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

	@PostMapping(value=CONTROLLER_URL+"/edit-order" )
	public ResponseEntity<?> editOrder(@RequestParam(name="orderId",required = false)  Long orderId, @RequestHeader("authorization") String token)  {
		long userId=utility.getUserIDByToken(token);
		ResponseEntityOutput response=new ResponseEntityOutput();
		Map map=new HashMap<>();

		try {
			response.CODE="1";
			response.USER_MESSAGE="Success";
			response.SYSTEM_MESSAGE="";
			response.DATA= OrdersService.editOrder(orderId,userId);

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

	@GetMapping(value=CONTROLLER_URL+"/previous-customer-order-details" )
	public ResponseEntity<?> getAllPreviousOrdersForCustomer(@RequestHeader("authorization") String token, Long customerId)  {
		long UserId=utility.getUserIDByToken(token);
		ResponseEntityOutput response=new ResponseEntityOutput();
		Map map=new HashMap<>();

		try {
			response.CODE="1";
			response.USER_MESSAGE="Success";
			response.SYSTEM_MESSAGE="";
			response.DATA= OrdersStatusService.getAllCustomersOrderHistory(customerId);

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