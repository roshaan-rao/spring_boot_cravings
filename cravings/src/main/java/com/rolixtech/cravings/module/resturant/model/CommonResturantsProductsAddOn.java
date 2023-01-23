package com.rolixtech.cravings.module.resturant.model;

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
public class CommonResturantsProductsAddOn {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long productId;	
	private long groupTypeId;
	private String groupTitle;
	private long selectionTypeId;
	private int minSelection;
	private int maxSelection;
	private int isDeleted;
	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getProductId() {
		return productId;
	}



	public void setProductId(long productId) {
		this.productId = productId;
	}



	public long getGroupTypeId() {
		return groupTypeId;
	}



	public void setGroupTypeId(long groupTypeId) {
		this.groupTypeId = groupTypeId;
	}



	public String getGroupTitle() {
		return groupTitle;
	}



	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}



	public long getSelectionTypeId() {
		return selectionTypeId;
	}



	public void setSelectionTypeId(long selectionTypeId) {
		this.selectionTypeId = selectionTypeId;
	}



	public int getMinSelection() {
		return minSelection;
	}



	public void setMinSelection(int minSelection) {
		this.minSelection = minSelection;
	}



	public int getMaxSelection() {
		return maxSelection;
	}



	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}


	

	public int getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}



	


	@Override
	public String toString() {
		return "CommonResturantsProductsAddOn [id=" + id + ", productId=" + productId + ", groupTypeId=" + groupTypeId
				+ ", groupTitle=" + groupTitle + ", selectionTypeId=" + selectionTypeId + ", minSelection="
				+ minSelection + ", maxSelection=" + maxSelection + ", isDeleted=" + isDeleted + "]";
	}



	public CommonResturantsProductsAddOn() {
		super();
	}



	
	
	
	
}
