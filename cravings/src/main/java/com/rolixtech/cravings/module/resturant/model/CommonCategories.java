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
public class CommonCategories {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String label;
	private String categoryImgUrl;
	private String directoryUrl;
	
	
	
	
	public CommonCategories(long id, String label, String categoryImgUrl, String directoryUrl) {
		super();
		this.id = id;
		this.label = label;
		this.categoryImgUrl = categoryImgUrl;
		this.directoryUrl = directoryUrl;
	}




	public CommonCategories(String label, String categoryImgUrl, String directoryUrl) {
		super();
		this.label = label;
		this.categoryImgUrl = categoryImgUrl;
		this.directoryUrl = directoryUrl;
	}




	@Override
	public String toString() {
		return "CommonCategories [id=" + id + ", label=" + label + ", categoryImgUrl=" + categoryImgUrl
				+ ", directoryUrl=" + directoryUrl + "]";
	}




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




	public String getCategoryImgUrl() {
		return categoryImgUrl;
	}




	public void setCategoryImgUrl(String categoryImgUrl) {
		this.categoryImgUrl = categoryImgUrl;
	}




	public String getDirectoryUrl() {
		return directoryUrl;
	}




	public void setDirectoryUrl(String directoryUrl) {
		this.directoryUrl = directoryUrl;
	}




	public CommonCategories() {
		super();
	}
	
	
	
	
}
