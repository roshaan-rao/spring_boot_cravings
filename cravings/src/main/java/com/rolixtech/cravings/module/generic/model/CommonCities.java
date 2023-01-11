package com.rolixtech.cravings.module.generic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonCities {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String label;
	private int isActive;
	private long provinceId;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public int getIsActive() {
		return isActive;
	}


	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}


	public long getProvinceId() {
		return provinceId;
	}


	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}


	@Override
	public String toString() {
		return "CommonCities [id=" + id + ", label=" + label + ", isActive=" + isActive + ", provinceId=" + provinceId
				+ "]";
	}


	

	
	 
	
}
