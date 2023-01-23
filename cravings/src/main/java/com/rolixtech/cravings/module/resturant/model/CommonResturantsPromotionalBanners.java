package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturantsPromotionalBanners {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Date startDate;
	private Date endDate;
	private int isActive;
	private Long createdBy;
	private Date createdOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "CommonResturantsPromotionalBanners [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", isActive=" + isActive + ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	
	
	
	
	
	
}
