package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonFeatures {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String label;
	private int isVisible;
	private int shortCode;
	private String url;
	private int sortOrder;
	private int groupId;
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
	public int getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}
	public int getShortCode() {
		return shortCode;
	}
	public void setShortCode(int shortCode) {
		this.shortCode = shortCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "CommonFeatures [id=" + id + ", label=" + label + ", isVisible=" + isVisible + ", shortCode=" + shortCode
				+ ", url=" + url + ", sortOrder=" + sortOrder + ", groupId=" + groupId + "]";
	}
	
	
	
	 
	
}