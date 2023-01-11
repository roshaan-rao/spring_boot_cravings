package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturantsDealsProducts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long dealId;
	private Long resturantProductId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDealId() {
		return dealId;
	}
	public void setDealId(long dealId) {
		this.dealId = dealId;
	}
	public Long getResturantProductId() {
		return resturantProductId;
	}
	public void setResturantProductId(Long resturantProductId) {
		this.resturantProductId = resturantProductId;
	}
	public CommonResturantsDealsProducts(long id, long dealId, Long resturantProductId) {
		super();
		this.id = id;
		this.dealId = dealId;
		this.resturantProductId = resturantProductId;
	}
	public CommonResturantsDealsProducts(long dealId, Long resturantProductId) {
		super();
		this.dealId = dealId;
		this.resturantProductId = resturantProductId;
	}
	
	
	public CommonResturantsDealsProducts() {
		super();
	}
	@Override
	public String toString() {
		return "CommonResturantsDealsProducts [id=" + id + ", dealId=" + dealId + ", resturantProductId="
				+ resturantProductId + "]";
	}
	
	
	
	 
	
}
