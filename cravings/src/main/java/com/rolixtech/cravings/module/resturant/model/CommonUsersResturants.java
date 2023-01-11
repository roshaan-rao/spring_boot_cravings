package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CommonUsersResturants {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;
	private long resturantId;
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getResturantId() {
		return resturantId;
	}


	public void setResturantId(long resturantId) {
		this.resturantId = resturantId;
	}


	public CommonUsersResturants(long userId, long resturantId) {
		super();
		this.userId = userId;
		this.resturantId = resturantId;
	}


	public CommonUsersResturants(long id, long userId, long resturantId) {
		super();
		this.id = id;
		this.userId = userId;
		this.resturantId = resturantId;
	}


	public CommonUsersResturants() {
		super();
	}
	
	
	
	
}
