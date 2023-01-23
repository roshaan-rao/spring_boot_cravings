package com.rolixtech.cravings.module.order.model;

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

@Entity
public class CustomerOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String orderNumber;
	private long resturantId;
	private double totalAmount;
	private double totalGst;
	private String orderType;
	private long userId;
	private Date createdOn;
	private long orderStatusId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	@Override
	public String toString() {
		return "CustomerOrder [id=" + id + ", orderNumber=" + orderNumber + ", resturantId=" + resturantId
				+ ", totalAmount=" + totalAmount + ", totalGst=" + totalGst + ", orderType=" + orderType + ", userId="
				+ userId + ", createdOn=" + createdOn + ", orderStatusId=" + orderStatusId + "]";
	}
	
	
	
	
	
	
}
