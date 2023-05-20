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
public class CustomerOrderChangeLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Long recordId; 
	private String orderNumber;
	private long resturantId;
	private double totalAmount;
	private double totalGst;
	private String orderType;
	private long userId;
	private Date createdOn;
	private long orderStatusId;
	private Long orderStatusChangedBy;
	private Date orderStatusChangedOn;
	private double subtotal;
	private double deliveryFee;
	private double discount;

	private String riderStatus;
	private String riderContactNumber;

	private String riderName;
	private Long logTypeId; 
	private String logReason;
	private long logCreatedBy;
	private Date logCreatedOn;
	public String getRiderContactNumber() {
		return riderContactNumber;
	}

	public void setRiderContactNumber(String riderContactNumber) {
		this.riderContactNumber = riderContactNumber;
	}
	public String getRiderStatus() {
		return riderStatus;
	}
	public String getRiderName() {
		return riderName;
	}

	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public void setRiderStatus(String riderStatus) {
		this.riderStatus = riderStatus;
	}
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
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Long getLogTypeId() {
		return logTypeId;
	}
	public void setLogTypeId(Long logTypeId) {
		this.logTypeId = logTypeId;
	}
	public String getLogReason() {
		return logReason;
	}
	public void setLogReason(String logReason) {
		this.logReason = logReason;
	}
	public long getLogCreatedBy() {
		return logCreatedBy;
	}
	public void setLogCreatedBy(long logCreatedBy) {
		this.logCreatedBy = logCreatedBy;
	}
	public Date getLogCreatedOn() {
		return logCreatedOn;
	}
	public void setLogCreatedOn(Date logCreatedOn) {
		this.logCreatedOn = logCreatedOn;
	}
	
	public Long getOrderStatusChangedBy() {
		return orderStatusChangedBy;
	}
	public void setOrderStatusChangedBy(Long orderStatusChangedBy) {
		this.orderStatusChangedBy = orderStatusChangedBy;
	}
	public Date getOrderStatusChangedOn() {
		return orderStatusChangedOn;
	}
	public void setOrderStatusChangedOn(Date orderStatusChangedOn) {
		this.orderStatusChangedOn = orderStatusChangedOn;
	}
	@Override
	public String toString() {
		return "CustomerOrderChangeLog [id=" + id + ", recordId=" + recordId + ", orderNumber=" + orderNumber
				+ ", resturantId=" + resturantId + ", totalAmount=" + totalAmount + ", totalGst=" + totalGst
				+ ", orderType=" + orderType + ", userId=" + userId + ", createdOn=" + createdOn + ", orderStatusId="
				+ orderStatusId + ", subtotal=" + subtotal + ", deliveryFee=" + deliveryFee + ", discount=" + discount
				+ ", logTypeId=" + logTypeId + ", logReason=" + logReason + ", logCreatedBy=" + logCreatedBy
				+ ", logCreatedOn=" + logCreatedOn + "]";
	}
	
	
	
	
}
