package com.rolixtech.cravings.module.order.POJO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


public class OrderProductsPOJO {


	private long productId;
	private int quantity;
	private double price;

	private List<OrderProductsOptionalAddOnPOJO> optionalAddOn = new ArrayList<>();
	private List<OrderProductsRequiredAddOnPOJO> requiredAddOn = new ArrayList<>();
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<OrderProductsOptionalAddOnPOJO> getOptionalAddOn() {
		return optionalAddOn;
	}
	public void setOptionalAddOn(ArrayList<OrderProductsOptionalAddOnPOJO> optionalAddOn) {
		this.optionalAddOn = optionalAddOn;
	}
	public List<OrderProductsRequiredAddOnPOJO> getRequiredAddOn() {
		return requiredAddOn;
	}
	public void setRequiredAddOn(ArrayList<OrderProductsRequiredAddOnPOJO> requiredAddOn) {
		this.requiredAddOn = requiredAddOn;
	}
	@Override
	public String toString() {
		return "OrderProductsPOJO [productId=" + productId + ", quantity=" + quantity + ", price=" + price
				+ ", optionalAddOn=" + optionalAddOn + ", requiredAddOn=" + requiredAddOn + "]";
	}


}