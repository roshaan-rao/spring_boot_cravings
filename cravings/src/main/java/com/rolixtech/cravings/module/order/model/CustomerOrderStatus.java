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
public class CustomerOrderStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String adminLabel;
	private String customerLabel;
	private String resturantLabel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAdminLabel() {
		return adminLabel;
	}
	public void setAdminLabel(String adminLabel) {
		this.adminLabel = adminLabel;
	}
	public String getCustomerLabel() {
		return customerLabel;
	}
	public void setCustomerLabel(String customerLabel) {
		this.customerLabel = customerLabel;
	}
	public String getResturantLabel() {
		return resturantLabel;
	}
	public void setResturantLabel(String resturantLabel) {
		this.resturantLabel = resturantLabel;
	}
	@Override
	public String toString() {
		return "CustomerOrderStatus [id=" + id + ", adminLabel=" + adminLabel + ", customerLabel=" + customerLabel
				+ ", resturantLabel=" + resturantLabel + "]";
	}
	
	
	
	
	
}
