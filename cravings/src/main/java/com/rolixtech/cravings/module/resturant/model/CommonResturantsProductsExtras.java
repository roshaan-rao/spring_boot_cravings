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
public class CommonResturantsProductsExtras {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long productId;

	private Long extraProductId;
	
	
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
	
	
	
	
	@Override
	public String toString() {
		return "CommonResturantsProductsExtras [id=" + id + ", productId=" + productId + ", extraProductId="
				+ extraProductId + "]";
	}
	public Long getExtraProductId() {
		return extraProductId;
	}
	public void setExtraProductId(Long extraProductId) {
		this.extraProductId = extraProductId;
	}
	
	
	public CommonResturantsProductsExtras(long productId, Long extraProductId) {
		super();
		this.productId = productId;
		this.extraProductId = extraProductId;
	}
	public CommonResturantsProductsExtras(long id, long productId, Long extraProductId) {
		super();
		this.id = id;
		this.productId = productId;
		this.extraProductId = extraProductId;
	}
	public CommonResturantsProductsExtras() {
		super();
	}
	
	
	
	
	
}
