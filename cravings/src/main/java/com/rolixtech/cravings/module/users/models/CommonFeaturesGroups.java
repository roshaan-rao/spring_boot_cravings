package com.rolixtech.cravings.module.users.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonFeaturesGroups {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String label;
	private String iconClass;
	private String iconName;
	private String ariaLabel;
	
	
	
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


	public String getIconClass() {
		return iconClass;
	}


	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}


	public String getAriaLabel() {
		return ariaLabel;
	}


	public void setAriaLabel(String ariaLabel) {
		this.ariaLabel = ariaLabel;
	}


	public String getIconName() {
		return iconName;
	}


	public void setIconName(String iconName) {
		this.iconName = iconName;
	}


	@Override
	public String toString() {
		return "CommonFeaturesGroups [id=" + id + ", label=" + label + ", iconClass=" + iconClass + ", iconName="
				+ iconName + ", ariaLabel=" + ariaLabel + "]";
	}


	
	
	 
	
}
