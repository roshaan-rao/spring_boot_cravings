package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturantsPromotionalBannersDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private long id;
	private long resturantId;
	private long promotionalBannerId;
	private String imageUrl;
	private long priority;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getResturantId() {
		return resturantId;
	}
	public void setResturantId(long resturantId) {
		this.resturantId = resturantId;
	}
	public long getPromotionalBannerId() {
		return promotionalBannerId;
	}
	public void setPromotionalBannerId(long promotionalBannerId) {
		this.promotionalBannerId = promotionalBannerId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	@Override
	public String toString() {
		return "CommonResturantsPromotionalBannersDetail [id=" + id + ", resturantId=" + resturantId
				+ ", promotionalBannerId=" + promotionalBannerId + ", imageUrl=" + imageUrl + ", priority=" + priority
				+ "]";
	}
	
}
