package com.rolixtech.cravings.module.order.POJO;

import java.util.ArrayList;
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


public class OrderPOJO {
	

	private long resturantId;
	private double totalAmount;
	private double totalGst;
	private String orderType;
	private long userId;
	
	private OrderAddressPOJO address;
	private ArrayList<OrderProductsPOJO> products = new ArrayList<>();
	public long getResturantId() {
		return resturantId;
	}
	public void setResturantId(long resturantId) {
		this.resturantId = resturantId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalGst() {
		return totalGst;
	}
	public void setTotalGst(double totalGst) {
		this.totalGst = totalGst;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public OrderAddressPOJO getAddress() {
		return address;
	}
	public void setAddress(OrderAddressPOJO address) {
		this.address = address;
	}
	public ArrayList<OrderProductsPOJO> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<OrderProductsPOJO> products) {
		this.products = products;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "OrderPOJO [resturantId=" + resturantId + ", totalAmount=" + totalAmount + ", totalGst=" + totalGst
				+ ", orderType=" + orderType + ", userId=" + userId + ", address=" + address + ", products=" + products
				+ "]";
	}
	

	
}
