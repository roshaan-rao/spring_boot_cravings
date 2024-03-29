package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class CommonResturantsProductsGroupTypes {
	
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





	@Override
	public String toString() {
		return "CommonResturantsProductsGroupTypes [id=" + id + ", label=" + label + "]";
	}



	public CommonResturantsProductsGroupTypes(long id, String label) {
		super();
		this.id = id;
		this.label = label;
	}



	public CommonResturantsProductsGroupTypes() {
		super();
	}
	
	 
	
}
