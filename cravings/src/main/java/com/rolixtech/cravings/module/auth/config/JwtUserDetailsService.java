package com.rolixtech.cravings.module.auth.config;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.models.CommonUsers;
import com.rolixtech.cravings.module.users.services.CommonRoleService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



@Service
public class JwtUserDetailsService implements UserDetailsService {

	 @Autowired
	 private CommonUsersDao UsersDao;
	 
	 @Autowired
	 private PasswordEncoder bcryptEncoder;
	 
	 @Autowired
	 private CommonRoleService roleService;
	 
	 @Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 CommonUsers user = UsersDao.findByEmailAndIsDeleted(username,0);
			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
			
			
			//return new org.springframework.security.core.userdetails.User(user.getEmail()+"", user.getPassword()+"",);
			//return new org.springframework.security.core.userdetails.User(user.getEmail()+"", user.getPassword()+"", getAuthority(user));
			return new User(user.getEmail()+"", user.getPassword()+"",	new ArrayList<>());
		}

	 
	 private Set<SimpleGrantedAuthority> getAuthority(CommonUsers user) {
		 System.out.println(user+"user");
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        user.getRoles().forEach(role -> {
	        	
	        	//System.out.println("role.getName()"+role.getName());
	            authorities.add(new SimpleGrantedAuthority(role.getName()));
	        });
	        return authorities;
	    }
	 
		public CommonUsers save(CommonUsers user) {
			CommonUsers newUser = new CommonUsers();
			newUser.setEmail(user.getEmail());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			return UsersDao.save(newUser);
		}
	
}