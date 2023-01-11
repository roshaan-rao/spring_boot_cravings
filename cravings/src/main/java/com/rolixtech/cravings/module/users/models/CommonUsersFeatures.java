package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonUsersFeatures {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;
	private long featureId;
	private long assignedBy;
	private Date assignedOn;
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
	public long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}
	public long getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(long assignedBy) {
		this.assignedBy = assignedBy;
	}
	public Date getAssignedOn() {
		return assignedOn;
	}
	public void setAssignedOn(Date assignedOn) {
		this.assignedOn = assignedOn;
	}
	@Override
	public String toString() {
		return "CommonUsersFeatures [id=" + id + ", userId=" + userId + ", featureId=" + featureId + ", assignedBy="
				+ assignedBy + ", assignedOn=" + assignedOn + "]";
	}
	
	
	 
	
}
