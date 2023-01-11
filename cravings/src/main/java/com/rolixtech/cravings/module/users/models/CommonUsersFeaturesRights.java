package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonUsersFeaturesRights {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String userFeatureId;
	private int rightTypeId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserFeatureId() {
		return userFeatureId;
	}
	public void setUserFeatureId(String userFeatureId) {
		this.userFeatureId = userFeatureId;
	}
	public int getRightTypeId() {
		return rightTypeId;
	}
	public void setRightTypeId(int rightTypeId) {
		this.rightTypeId = rightTypeId;
	}
	@Override
	public String toString() {
		return "CommonUsersFeaturesRights [id=" + id + ", userFeatureId=" + userFeatureId + ", rightTypeId="
				+ rightTypeId + "]";
	}
	
	
	 
	
}
