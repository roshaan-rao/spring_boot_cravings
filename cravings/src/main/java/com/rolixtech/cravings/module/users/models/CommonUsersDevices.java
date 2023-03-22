package com.rolixtech.cravings.module.users.models;

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
public class CommonUsersDevices {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;
	private String deviceId;
	private Date createdOn;
	
	
	
	

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





	public String getDeviceId() {
		return deviceId;
	}





	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}





	public Date getCreatedOn() {
		return createdOn;
	}





	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}





	public CommonUsersDevices() {
		super();
	}
	
	
	
	
}
