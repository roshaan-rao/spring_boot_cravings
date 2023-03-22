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
	private double totalGstPercentage;
	private int deliveryTime;
	private String orderType;
	private long userId;
	private double subtotal;
	private double deliveryFee;
	private double discount;
	private double serviceFee;
	private String instructions;
	private long voucherId;
	private Integer isAdmin; 
	
	
	
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
	
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public long getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}
	@Override
	public String toString() {
		return "OrderPOJO [resturantId=" + resturantId + ", totalAmount=" + totalAmount + ", totalGst=" + totalGst
				+ ", totalGstPercentage=" + totalGstPercentage + ", deliveryTime=" + deliveryTime + ", orderType="
				+ orderType + ", userId=" + userId + ", subtotal=" + subtotal + ", deliveryFee=" + deliveryFee
				+ ", discount=" + discount + ", serviceFee=" + serviceFee + ", instructions=" + instructions
				+ ", voucherId=" + voucherId + ", isAdmin=" + isAdmin + ", address=" + address + ", products="
				+ products + "]";
	}
	
	
	
}
