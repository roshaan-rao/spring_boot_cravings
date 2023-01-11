package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturantsDeals {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String label;
	private Long resturantId;
	private Date startDate;
	private Date startDateTime;
	private Date startTime;
	private Date endDateTime;
	private Date endDate;
	private Date endTime;
	private double totalPrice;
	
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

	public Long getResturantId() {
		return resturantId;
	}

	public void setResturantId(Long resturantId) {
		this.resturantId = resturantId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	public CommonResturantsDeals(String label, Long resturantId, Date startDate, Date startDateTime, Date startTime,
			Date endDateTime, Date endDate, Date endTime, double totalPrice) {
		super();
		this.label = label;
		this.resturantId = resturantId;
		this.startDate = startDate;
		this.startDateTime = startDateTime;
		this.startTime = startTime;
		this.endDateTime = endDateTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.totalPrice = totalPrice;
	}

	public CommonResturantsDeals(long id, String label, Long resturantId, Date startDate, Date startDateTime,
			Date startTime, Date endDateTime, Date endDate, Date endTime, double totalPrice) {
		super();
		this.id = id;
		this.label = label;
		this.resturantId = resturantId;
		this.startDate = startDate;
		this.startDateTime = startDateTime;
		this.startTime = startTime;
		this.endDateTime = endDateTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.totalPrice = totalPrice;
	}

	public CommonResturantsDeals() {
		super();
	}

	@Override
	public String toString() {
		return "CommonResturantsDeals [id=" + id + ", label=" + label + ", resturantId=" + resturantId + ", startDate="
				+ startDate + ", startDateTime=" + startDateTime + ", startTime=" + startTime + ", endDateTime="
				+ endDateTime + ", endDate=" + endDate + ", endTime=" + endTime + ", totalPrice=" + totalPrice + "]";
	}
	
	
	 
	
}
