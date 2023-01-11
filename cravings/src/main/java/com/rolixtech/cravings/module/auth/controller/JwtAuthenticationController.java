package com.rolixtech.cravings.module.auth.controller;

import com.rolixtech.cravings.module.auth.config.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.JwtResponse;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonUsers;
//import com.rolixtech.cravings.module.auth.model.;
import com.rolixtech.cravings.module.auth.config.JwtUserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private CommonUsersDao UsersDao;
	
	@Autowired
	private CommonUsersResturantsDao UsersResturantsDao;
	
	@Autowired
	private GenericUtility utility;

	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/authentication";
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = CONTROLLER_URL, method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(String email, String password, String portal) throws Exception {
		//System.out.println(authenticationRequest.getUsername()+ " - "+ authenticationRequest.getPassword());
		
		
		authenticate(email, password);
		
		final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)); 
				
				
				
        SecurityContextHolder.getContext().setAuthentication(authentication);
       
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		 final String token = jwtTokenUtil.generateToken(userDetails);
		CommonUsers User=UsersDao.findByEmailAndIsDeleted(email,0);

        
        ResponseEntityOutput response=new ResponseEntityOutput();
        
        List<Map> RowOutput=new ArrayList<>();
        Map Row=new HashMap<>();
        Row.put("token", token);
        Row.put("userId",  User.getId());
        Row.put("userName", User.getFirstName()+" "+User.getLastName());
        Row.put("userEmail", User.getEmail());
        Row.put("profileImage", User.getProfileImgUrl());
       
        CommonUsersResturants resturant=UsersResturantsDao.findByUserId(User.getId());
        if(resturant!=null) {
        	Row.put("resturantId", resturant.getResturantId());
        }else {
        	Row.put("resturantId",0);
        }
        
       
        
        Set<CommonRole> role= User.getRoles();
        List<CommonRole> list = new ArrayList<CommonRole>(role);
        CommonRole obj = list.get(0);
        System.out.println("Role"+obj.getId());
        System.out.println("PORTAL"+portal);
        
        
       
    	if(utility.parseLong(obj.getId())!=utility.parseLong(portal)) {
    		 Map Row2=new HashMap<>();
				//RowOutput.add(Row2);
		    	response.CODE="2";
				response.USER_MESSAGE="User Not Allowed For This Protal";
				response.SYSTEM_MESSAGE="Role Does'nt Matched";
    		
        }else {
        	if(obj!=null) {
        		Row.put("roleId", obj.getId());
        		Row.put("roleLabel",obj.getName());
        		response.CODE="1";
        		response.USER_MESSAGE="Logged In";
        		response.SYSTEM_MESSAGE="Credientials Matched";
        		RowOutput.add(Row);
            }else{
            	Map Row2=new HashMap<>(); 
				//RowOutput.add(Row2);
		    	response.CODE="2";
				response.USER_MESSAGE="User Not Allowed For This Protal";
				response.SYSTEM_MESSAGE="";
            }
        	
        	
        }
		
    	response.DATA=RowOutput;		
		return ResponseEntity.status(HttpStatus.OK).body(response);
      
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			
		    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		    bCryptPasswordEncoder.encode(password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User is disabled, please contact IT Support");
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid username or password");
		}
	}
	
}
