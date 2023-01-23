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
public class CommonResturantsProductsAddOnDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Long productAddOnId;
	private long productId;	
	private double price;	
	
	
	
	
	
	@Override
	public String toString() {
		return "CommonResturantsProductsAddOnDetail [id=" + id + ", productAddOnId=" + productAddOnId + ", productId="
				+ productId + ", price=" + price + "]";
	}





	public long getId() {
		return id;
	}





	public void setId(long id) {
		this.id = id;
	}





	public Long getProductAddOnId() {
		return productAddOnId;
	}





	public void setProductAddOnId(Long productAddOnId) {
		this.productAddOnId = productAddOnId;
	}





	public long getProductId() {
		return productId;
	}





	public void setProductId(long productId) {
		this.productId = productId;
	}





	public double getPrice() {
		return price;
	}





	public void setPrice(double price) {
		this.price = price;
	}





	public CommonResturantsProductsAddOnDetail() {
		super();
	}
	
	
	
	
	
}
