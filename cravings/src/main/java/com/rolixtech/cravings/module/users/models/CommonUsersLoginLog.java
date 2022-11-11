package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonUsersLoginLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;
	private long platformId;
	private long loginTypeId;
	private Date attemptedOn;
	private long lat;
	private long lng;
	private double accuracy;
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
	public long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}
	public long getLoginTypeId() {
		return loginTypeId;
	}
	public void setLoginTypeId(long loginTypeId) {
		this.loginTypeId = loginTypeId;
	}
	public Date getAttemptedOn() {
		return attemptedOn;
	}
	public void setAttemptedOn(Date attemptedOn) {
		this.attemptedOn = attemptedOn;
	}
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	@Override
	public String toString() {
		return "CommonUsersLoginLog [id=" + id + ", userId=" + userId + ", platformId=" + platformId + ", loginTypeId="
				+ loginTypeId + ", attemptedOn=" + attemptedOn + ", lat=" + lat + ", lng=" + lng + ", accuracy="
				+ accuracy + "]";
	}
	
	
	
	 
	
}
