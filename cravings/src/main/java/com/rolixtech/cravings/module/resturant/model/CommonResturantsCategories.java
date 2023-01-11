package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturantsCategories {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long resturantId;
	private long categoryId;
	private String label;
	private int isActive;
	private int isDeleted;
	
	

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



	public long getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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



	



	public int getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}



	public CommonResturantsCategories(long id, long resturantId, long categoryId, String label, int isActive,
			int isDeleted) {
		super();
		this.id = id;
		this.resturantId = resturantId;
		this.categoryId = categoryId;
		this.label = label;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}



	public CommonResturantsCategories(long resturantId, long categoryId, String label, int isActive, int isDeleted) {
		super();
		this.resturantId = resturantId;
		this.categoryId = categoryId;
		this.label = label;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}



	@Override
	public String toString() {
		return "CommonResturantsCategories [id=" + id + ", resturantId=" + resturantId + ", categoryId=" + categoryId
				+ ", label=" + label + ", isActive=" + isActive + ", isDeleted=" + isDeleted + "]";
	}



	public CommonResturantsCategories() {
		super();
	}
	
	 
	
}
