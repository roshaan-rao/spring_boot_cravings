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
	private String label;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() { 
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public CustomerOrderStatus() {
		super();
	}
	public CustomerOrderStatus(long id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	@Override
	public String toString() {
		return "CustomerOrderStatus [id=" + id + ", label=" + label + "]";
	}
	
	
	
	
	
	
}
