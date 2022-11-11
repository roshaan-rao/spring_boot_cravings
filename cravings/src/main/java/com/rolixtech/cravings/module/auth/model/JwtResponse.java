package com.rolixtech.cravings.module.auth.model;

import java.io.Serializable;
import java.util.List;



public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Long id;
	private final String username ;
	private final String email ;
	private final List<String> roles;
	
	public JwtResponse(String jwttoken, long id, String username, String email, java.util.List<String> authorities) {
		super();
		this.jwttoken = jwttoken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = authorities;
	}
	
	public String getJwttoken() {
		return jwttoken;
	}
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public List getRoles() {
		return roles;
	} 
	

	
}