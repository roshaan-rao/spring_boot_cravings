package com.rolixtech.cravings.module.order.model;

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
public class CustomerOrderProductsOptionalAddOn{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long orderProductId;
	private long productId;
	private double price;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	
	public long getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(long orderProductId) {
		this.orderProductId = orderProductId;
	}
	@Override
	public String toString() {
		return "OrderProductsOptionalAddOn [id=" + id + ", productId=" + productId + ", price=" + price + "]";
	}

	public CustomerOrderProductsOptionalAddOn(long orderProductId, long productId, double price) {
		super();
		this.orderProductId = orderProductId;
		this.productId = productId;
		this.price = price;
	}
	public CustomerOrderProductsOptionalAddOn() {
		super();
	}
	
	
}
