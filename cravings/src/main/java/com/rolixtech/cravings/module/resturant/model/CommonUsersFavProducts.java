package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonUsersFavProducts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;
	private long productId;
	
	
	
	
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




	public long getProductId() {
		return productId;
	}




	public void setProductId(long productId) {
		this.productId = productId;
	}


 

	public CommonUsersFavProducts(long userId, long productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}




	public CommonUsersFavProducts() {
		super();
	}
	
	 
	
}
