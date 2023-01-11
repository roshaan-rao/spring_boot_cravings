package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class CommonResturantsProducts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String label;
	private String description;
	private long resturantId;
	private double salesTax;
	private double salesTaxPercentage;
	private double grossAmount;
	private double netAmount;
	private double discount;
	private double rate;
	private int isTimingEnable;
	private Date availabilityFrom;
	private Date availabilityTo;
	private int isAvailable;
	private int isActive;
	private String productImgUrl;
	private double rating;
	private int isExtra;
	private long resturantCategoryId;
	private int isDeleted;
	
	
	

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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public long getResturantId() {
		return resturantId;
	}



	public void setResturantId(long resturantId) {
		this.resturantId = resturantId;
	}



	public double getSalesTax() {
		return salesTax;
	}



	public void setSalesTax(double salesTax) {
		this.salesTax = salesTax;
	}



	public double getSalesTaxPercentage() {
		return salesTaxPercentage;
	}



	public void setSalesTaxPercentage(double salesTaxPercentage) {
		this.salesTaxPercentage = salesTaxPercentage;
	}



	public double getGrossAmount() {
		return grossAmount;
	}



	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}



	public double getNetAmount() {
		return netAmount;
	}



	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}



	public double getDiscount() {
		return discount;
	}



	public void setDiscount(double discount) {
		this.discount = discount;
	}



	public double getRate() {
		return rate;
	}



	public void setRate(double rate) {
		this.rate = rate;
	}



	public int getIsTimingEnable() {
		return isTimingEnable;
	}



	public void setIsTimingEnable(int isTimingEnable) {
		this.isTimingEnable = isTimingEnable;
	}



	public Date getAvailabilityFrom() {
		return availabilityFrom;
	}



	public void setAvailabilityFrom(Date availabilityFrom) {
		this.availabilityFrom = availabilityFrom;
	}



	public Date getAvailabilityTo() {
		return availabilityTo;
	}



	public void setAvailabilityTo(Date availabilityTo) {
		this.availabilityTo = availabilityTo;
	}



	public int getIsAvailable() {
		return isAvailable;
	}



	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}



	public int getIsActive() {
		return isActive;
	}



	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}



	public String getProductImgUrl() {
		return productImgUrl;
	}



	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}



	public double  getRating() {
		return rating;
	}



	public void setRating(double rating) {
		this.rating = rating;
	}



	public long getResturantCategoryId() {
		return resturantCategoryId;
	}



	public void setResturantCategoryId(long resturantCategoryId) {
		this.resturantCategoryId = resturantCategoryId;
	}


	


	public int getIsExtra() {
		return isExtra;
	}



	public void setIsExtra(int isExtra) {
		this.isExtra = isExtra;
	}



	public int getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}



	

	public CommonResturantsProducts(long id, String label, String description, long resturantId, double salesTax,
			double salesTaxPercentage, double grossAmount, double netAmount, double discount, double rate,
			int isTimingEnable, Date availabilityFrom, Date availabilityTo, int isAvailable, int isActive,
			String productImgUrl, double rating, int isExtra, long resturantCategoryId, int isDeleted) {
		super();
		this.id = id;
		this.label = label;
		this.description = description;
		this.resturantId = resturantId;
		this.salesTax = salesTax;
		this.salesTaxPercentage = salesTaxPercentage;
		this.grossAmount = grossAmount;
		this.netAmount = netAmount;
		this.discount = discount;
		this.rate = rate;
		this.isTimingEnable = isTimingEnable;
		this.availabilityFrom = availabilityFrom;
		this.availabilityTo = availabilityTo;
		this.isAvailable = isAvailable;
		this.isActive = isActive;
		this.productImgUrl = productImgUrl;
		this.rating = rating;
		this.isExtra = isExtra;
		this.resturantCategoryId = resturantCategoryId;
		this.isDeleted = isDeleted;
	}



	public CommonResturantsProducts(String label, String description, long resturantId, double salesTax,
			double salesTaxPercentage, double grossAmount, double netAmount, double discount, double rate,
			int isTimingEnable, Date availabilityFrom, Date availabilityTo, int isAvailable, int isActive,
			String productImgUrl, double rating, int isExtra, long resturantCategoryId, int isDeleted) {
		super();
		this.label = label;
		this.description = description;
		this.resturantId = resturantId;
		this.salesTax = salesTax;
		this.salesTaxPercentage = salesTaxPercentage;
		this.grossAmount = grossAmount;
		this.netAmount = netAmount;
		this.discount = discount;
		this.rate = rate;
		this.isTimingEnable = isTimingEnable;
		this.availabilityFrom = availabilityFrom;
		this.availabilityTo = availabilityTo;
		this.isAvailable = isAvailable;
		this.isActive = isActive;
		this.productImgUrl = productImgUrl;
		this.rating = rating;
		this.isExtra = isExtra;
		this.resturantCategoryId = resturantCategoryId;
		this.isDeleted = isDeleted;
	}



	@Override
	public String toString() {
		return "CommonResturantsProducts [id=" + id + ", label=" + label + ", description=" + description
				+ ", resturantId=" + resturantId + ", salesTax=" + salesTax + ", salesTaxPercentage="
				+ salesTaxPercentage + ", grossAmount=" + grossAmount + ", netAmount=" + netAmount + ", discount="
				+ discount + ", rate=" + rate + ", isTimingEnable=" + isTimingEnable + ", availabilityFrom="
				+ availabilityFrom + ", availabilityTo=" + availabilityTo + ", isAvailable=" + isAvailable
				+ ", isActive=" + isActive + ", productImgUrl=" + productImgUrl + ", rating=" + rating + ", isExtra="
				+ isExtra + ", resturantCategoryId=" + resturantCategoryId + ", isDeleted=" + isDeleted + "]";
	}



	public CommonResturantsProducts() {
		super();
	}
	
	 
	
}
