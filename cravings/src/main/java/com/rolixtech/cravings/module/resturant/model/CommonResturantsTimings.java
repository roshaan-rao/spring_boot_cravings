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
public class CommonResturantsTimings {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long dayId;
	private Date openingTime;
	private Date closingTime;
	private long resturantId;

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getDayId() {
		return dayId;
	}



	public void setDayId(long dayId) {
		this.dayId = dayId;
	}



	public Date getOpeningTime() {
		return openingTime;
	}



	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}



	public Date getClosingTime() {
		return closingTime;
	}



	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}



	public long getResturantId() {
		return resturantId;
	}



	public void setResturantId(long resturantId) {
		this.resturantId = resturantId;
	}



	public CommonResturantsTimings(long dayId, Date openingTime, Date closingTime, long resturantId) {
		super();
		this.dayId = dayId;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.resturantId = resturantId;
	}



	public CommonResturantsTimings(long id, long dayId, Date openingTime, Date closingTime, long resturantId) {
		super();
		this.id = id;
		this.dayId = dayId;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.resturantId = resturantId;
	}



	public CommonResturantsTimings() {
		super();
	}
	
	
	
	
}
