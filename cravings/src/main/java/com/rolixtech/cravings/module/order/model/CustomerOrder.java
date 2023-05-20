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
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private long id;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
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
	private double totalGstPercentage;
	private int deliveryTime;
	private double serviceFee;
	private String instructions;
	private long voucherId;
	private Integer isHelpRequired;
	private Integer isAdmin;
	private Long orderCreatedByAdminId;

	private String riderName;
	private String riderStatus;
	private String acknowledgedBy;
	private String riderContactNumber;



	private boolean isFirstOrder;

	public boolean isFirstOrder() {
		return isFirstOrder;
	}

	public void setFirstOrder(boolean firstOrder) {
		isFirstOrder = firstOrder;
	}
	private boolean isLocked;
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	public String getRiderContactNumber() {
		return riderContactNumber;
	}

	public void setRiderContactNumber(String riderContactNumber) {
		this.riderContactNumber = riderContactNumber;
	}
	public String getAcknowledgedBy() {
		return acknowledgedBy;
	}

	public void setAcknowledgedBy(String acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}

	public String getRiderStatus() {
		return riderStatus;
	}

	public void setRiderStatus(String riderStatus) {
		this.riderStatus = riderStatus;
	}
	public String getRiderName() {
		return riderName;
	}

	public void setRiderName(String riderName) {
		this.riderName = riderName;
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
	public double getTotalGstPercentage() {
		return totalGstPercentage;
	}
	public void setTotalGstPercentage(double totalGstPercentage) {
		this.totalGstPercentage = totalGstPercentage;
	}
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public double getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public long getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}
	
	
	public Integer getIsHelpRequired() {
		return isHelpRequired;
	}
	public void setIsHelpRequired(Integer isHelpRequired) {
		this.isHelpRequired = isHelpRequired;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	public Long getOrderCreatedByAdminId() {
		return orderCreatedByAdminId;
	}
	public void setOrderCreatedByAdminId(Long orderCreatedByAdminId) {
		this.orderCreatedByAdminId = orderCreatedByAdminId;
	}
	@Override
	public String toString() {
		return "CustomerOrder [id=" + id + ", orderNumber=" + orderNumber + ", resturantId=" + resturantId
				+ ", totalAmount=" + totalAmount + ", totalGst=" + totalGst + ", orderType=" + orderType + ", userId="
				+ userId + ", createdOn=" + createdOn + ", orderStatusId=" + orderStatusId + ", orderStatusChangedBy="
				+ orderStatusChangedBy + ", orderStatusChangedOn=" + orderStatusChangedOn + ", subtotal=" + subtotal
				+ ", deliveryFee=" + deliveryFee + ", discount=" + discount + ", totalGstPercentage="
				+ totalGstPercentage + ", deliveryTime=" + deliveryTime + ", serviceFee=" + serviceFee
				+ ", instructions=" + instructions + ", voucherId=" + voucherId + ", isHelpRequired=" + isHelpRequired
				+ ", isAdmin=" + isAdmin + ", orderCreatedByAdminId=" + orderCreatedByAdminId + "]";
	}
	
	
	
}
