package com.rolixtech.cravings.module.auth.controller;

import com.rolixtech.cravings.module.auth.config.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.rolixtech.cravings.module.auth.model.JwtRequest;
import com.rolixtech.cravings.module.auth.model.JwtResponse;
import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.models.CommonUsers;
//import com.rolixtech.cravings.module.auth.model.;
import com.rolixtech.cravings.module.auth.config.JwtUserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/authentication";
	
	
	
//	public ResponseEntity<?> createAuthenticationTokeno(@RequestBody JwtRequest authenticationRequest) throws Exception {
//
//		final Authentication authentication = authenticate(authenticationRequest.getUsername(),
//        		authenticationRequest.getPassword());
//				
//				
////				authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                		authenticationRequest.getUsername(),
////                		authenticationRequest.getPassword()
////                )
////        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String token = jwtTokenUtil.generateToken(authentication);
//        
//        CommonUsers Users=UsersDao.findByEmail(authenticationRequest.getUsername());
//        List<String> authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new JwtResponse(token, Users.getId(), Users.getFirstName()+" "+Users.getLastName(), Users.getEmail(), authorities));
//	}
//
////	@RequestMapping(value = "/register", method = RequestMethod.POST)
////	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
////		return ResponseEntity.ok(userDetailsService.save(user));
////	}
//
//	
//	private Authentication authenticate1(String username, String password) throws Exception{
//		Authentication authentication=null;
//		//try {
////			System.out.println("YOOOOOOO"+authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
//			//authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////		} catch (DisabledException e) {
////			throw new Exception("USER_DISABLED", e);
////		} catch (BadCredentialsException e) {
////			throw new Exception("INVALID_CREDENTIALS", e);
////		}
//			
//		  try {
//	            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//	            authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//	        } catch (BadCredentialsException e) {
//	            //Here is the error
//	            throw new Exception("Incorrect username or password", e);
//	        }	
//			
//		return authentication;
//	}
	
	
	
	
	@RequestMapping(value = CONTROLLER_URL, method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println(authenticationRequest.getUsername()+ " - "+ authenticationRequest.getPassword());
		
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		authenticationRequest.getUsername(),
                		authenticationRequest.getPassword()
                )
        ); 
				
				
				
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		CommonUsers User=UsersDao.findByEmail(authenticationRequest.getUsername());
	
		
        CommonUsers Users=UsersDao.findByEmail(authenticationRequest.getUsername());
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(token, Users.getId(), Users.getFirstName()+" "+Users.getLastName(), Users.getEmail(), authorities));
	
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
