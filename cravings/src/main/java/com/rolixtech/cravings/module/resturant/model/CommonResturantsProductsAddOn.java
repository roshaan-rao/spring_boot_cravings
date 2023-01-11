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
public class CommonResturantsProductsAddOn {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long productId;

	
	private Long addOnProductId;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public Long getAddOnProductId() {
		return addOnProductId;
	}
	public void setAddOnProductId(Long addOnProductId) {
		this.addOnProductId = addOnProductId;
	}
	
	
	
	
	public CommonResturantsProductsAddOn() {
		super();
	}
	@Override
	public String toString() {
		return "CommonResturantsProductsAddOn [id=" + id + ", productId=" + productId + ", addOnProductId="
				+ addOnProductId + "]";
	}
	
	
	
	
	
}
