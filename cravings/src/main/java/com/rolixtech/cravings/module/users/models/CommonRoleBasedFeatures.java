package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonRoleBasedFeatures {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long roleId;
	private long featureId;
	private long assignedBy;
	private Date assignedOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
		return "CommonRoleBasedFeatures [id=" + id + ", roleId=" + roleId + ", featureId=" + featureId + ", assignedBy="
				+ assignedBy + ", assignedOn=" + assignedOn + "]";
	}
	
	
	 
	
}
