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
import com.rolixtech.cravings.module.users.services.CommonRoleBasedFeaturesService;
import com.rolixtech.cravings.module.users.services.CommonRoleService;
import com.rolixtech.cravings.module.users.services.CommonUsersFeaturesService;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import com.rolixtech.cravings.module.users.services.CommonUsersStatusesService;
@RestController
@CrossOrigin()
public class CommonRoleBasedFeatureRightsController {
   
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/admin/role/features";
	
	
	@Autowired
	private CommonUsersService UsersService;
	
	
	@Autowired
	private CommonRoleService RoleService;
	
	@Autowired
	private CommonUsersStatusesService StatusesService;
	

	@Autowired
	private CommonRoleBasedFeaturesService RoleBasedFeaturesService;
	 
	@Autowired
	private GenericUtility Utility; 
	
	 @GetMapping(CONTROLLER_URL+"/roles-view")
		public ResponseEntity<?> UsersView(@RequestHeader("authorization") String token)  { 
				
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					
					response.CODE="1";
					response.USER_MESSAGE="";
					response.SYSTEM_MESSAGE="";
					response.DATA=RoleService.getAll();
				
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
	  //NOT IN USE   
	 @GetMapping(CONTROLLER_URL+"/view.")
		public ResponseEntity<?> View(@RequestHeader("authorization") String token)  { 
				
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					
					response.CODE="1";
					response.USER_MESSAGE="";
					response.SYSTEM_MESSAGE="";
					response.DATA=RoleBasedFeaturesService.getAllFeaturesGroupWise();
				
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
	 
	  
	 @PostMapping(CONTROLLER_URL+"/save")
	public ResponseEntity<?> Save(Long[] roleIds,Long[] roleRightsIds,@RequestHeader("authorization") String token)  { 
		        long UserID =Utility.getUserIDByToken(token);
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					
					response.CODE="1";
					response.USER_MESSAGE="";
					response.SYSTEM_MESSAGE="";
					RoleBasedFeaturesService.Save(Utility.parseLong(roleIds),Utility.parseLong(roleRightsIds),UserID);
				
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
	 
	 
	@PostMapping(CONTROLLER_URL+"/single-role-view")
	public ResponseEntity<?> View(Long roleId,@RequestHeader("authorization") String token)  { 
			        long UserID =Utility.getUserIDByToken(token);
					ResponseEntityOutput response=new ResponseEntityOutput();
					Map map=new HashMap<>();
					
					try {
						
						response.CODE="1";
						response.USER_MESSAGE="";
						response.SYSTEM_MESSAGE="";
						response.DATA= RoleBasedFeaturesService.getAllRoleAssignedFeaturesGroupWiseWithAllFeaturesForEdit(Utility.parseLong(roleId));
					
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
	
	
	@GetMapping(CONTROLLER_URL+"/all-users-view")
	public ResponseEntity<?> Save(@RequestHeader("authorization") String token)  { 
	        long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA= UsersService.getAllInternalUsersUsersAssignedFeaturesList();
			
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
	
	
	@DeleteMapping(CONTROLLER_URL+"/delete-all")
	public ResponseEntity<?> Delete(Long roleId,@RequestHeader("authorization") String token)  { 
	        long UserID =Utility.getUserIDByToken(token);
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="Deleted";
				response.SYSTEM_MESSAGE="";
				RoleBasedFeaturesService.deleteAllRoleBasedFeaturesRights(Utility.parseLong(roleId));
			
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