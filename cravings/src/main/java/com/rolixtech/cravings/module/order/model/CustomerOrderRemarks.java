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
public class CustomerOrderRemarks {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String remarks;
	private Long remarksAddedBy;
	private Date remarksAddedOn;
	private Long orderId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getRemarksAddedBy() {
		return remarksAddedBy;
	}
	public void setRemarksAddedBy(Long remarksAddedBy) {
		this.remarksAddedBy = remarksAddedBy;
	}
	public Date getRemarksAddedOn() {
		return remarksAddedOn;
	}
	public void setRemarksAddedOn(Date remarksAddedOn) {
		this.remarksAddedOn = remarksAddedOn;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "CustomerOrderRemarks [id=" + id + ", remarks=" + remarks + ", remarksAddedBy=" + remarksAddedBy
				+ ", remarksAddedOn=" + remarksAddedOn + ", orderId=" + orderId + "]";
	}
	
	
	
	
	
}
