package com.rolixtech.cravings.module.resturant.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonResturants {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	private String label;
	private String address;
	private String contactNo;
	private String email;
	private long countryId;
	private long provinceId;
	private long cityId;
	private double latitude;
	private double longitude;
	private double accuracy;
	private int isActive;
	private String logoImgUrl;
	private String profileImgUrl;
	private String bannerImgUrl;
	private String directoryUrl;
	private double rating;
	private Long activatedBy; 
	private Date activatedOn;
	private Long deactivatedBy;
	private Date deactivatedOn; 
	private Integer status; 
	private Long statusChangedBy; 
	private Date statusChangedOn;
	private int isDeleted;
	private int isGst;
	private Double gstPercentage;
	private Double deliveryCharges;
	private String contactNo2;
	private String contactNo3;
	private String contactNo4;
	private Integer deliveryTime;
	private Double discount;
	
	 

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




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public String getContactNo() {
		return contactNo;
	}




	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public long getCountryId() {
		return countryId;
	}




	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}




	public long getProvinceId() {
		return provinceId;
	}




	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}




	public long getCityId() {
		return cityId;
	}




	public void setCityId(long cityId) {
		this.cityId = cityId;
	}




	public double getLatitude() {
		return latitude;
	}




	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}




	public double getLongitude() {
		return longitude;
	}




	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}




	public double getAccuracy() {
		return accuracy;
	}




	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}




	public int getIsActive() {
		return isActive;
	}




	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}




	public String getLogoImgUrl() {
		return logoImgUrl;
	}




	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}




	public String getProfileImgUrl() {
		return profileImgUrl;
	}




	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}




	public String getBannerImgUrl() {
		return bannerImgUrl;
	}




	public void setBannerImgUrl(String bannerImgUrl) {
		this.bannerImgUrl = bannerImgUrl;
	}




	public String getDirectoryUrl() {
		return directoryUrl;
	}




	public void setDirectoryUrl(String directoryUrl) {
		this.directoryUrl = directoryUrl;
	}




	public double getRating() {
		return rating;
	}




	public void setRating(double rating) {
		this.rating = rating;
	}




	public Long getActivatedBy() {
		return activatedBy;
	}




	public void setActivatedBy(Long activatedBy) {
		this.activatedBy = activatedBy;
	}




	public Date getActivatedOn() {
		return activatedOn;
	}




	public void setActivatedOn(Date activatedOn) {
		this.activatedOn = activatedOn;
	}




	public Long getDeactivatedBy() {
		return deactivatedBy;
	}




	public void setDeactivatedBy(Long deactivatedBy) {
		this.deactivatedBy = deactivatedBy;
	}




	public Date getDeactivatedOn() {
		return deactivatedOn;
	}




	public void setDeactivatedOn(Date deactivatedOn) {
		this.deactivatedOn = deactivatedOn;
	}




	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}




	public Long getStatusChangedBy() {
		return statusChangedBy;
	}




	public void setStatusChangedBy(Long statusChangedBy) {
		this.statusChangedBy = statusChangedBy;
	}




	public Date getStatusChangedOn() {
		return statusChangedOn;
	}




	public void setStatusChangedOn(Date statusChangedOn) {
		this.statusChangedOn = statusChangedOn;
	}




	public int getIsDeleted() {
		return isDeleted;
	}




	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}



	public int getIsGst() {
		return isGst;
	}




	public void setIsGst(int isGst) {
		this.isGst = isGst;
	}




	public Double getGstPercentage() {
		return gstPercentage;
	}




	public void setGstPercentage(Double gstGstPercentage) {
		this.gstPercentage = gstGstPercentage;
	}




	public Double getDeliveryCharges() {
		return deliveryCharges;
	}




	public void setDeliveryCharges(Double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}




	public String getContactNo2() {
		return contactNo2;
	}




	public void setContactNo2(String contactNo2) {
		this.contactNo2 = contactNo2;
	}




	public String getContactNo3() {
		return contactNo3;
	}




	public void setContactNo3(String contactNo3) {
		this.contactNo3 = contactNo3;
	}




	public String getContactNo4() {
		return contactNo4;
	}




	public void setContactNo4(String contactNo4) {
		this.contactNo4 = contactNo4;
	}

	


	public CommonResturants(long id, String label, String address, String contactNo, String email, long countryId,
			long provinceId, long cityId, double latitude, double longitude, double accuracy, int isActive,
			String logoImgUrl, String profileImgUrl, String bannerImgUrl, String directoryUrl, double rating,
			Long activatedBy, Date activatedOn, Long deactivatedBy, Date deactivatedOn, Integer status,
			Long statusChangedBy, Date statusChangedOn, int isDeleted, int isGst, Double gstPercentage,
			Double deliveryCharges, String contactNo2, String contactNo3, String contactNo4, Integer deliveryTime,
			Double discount) {
		super();
		this.id = id;
		this.label = label;
		this.address = address;
		this.contactNo = contactNo;
		this.email = email;
		this.countryId = countryId;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.isActive = isActive;
		this.logoImgUrl = logoImgUrl;
		this.profileImgUrl = profileImgUrl;
		this.bannerImgUrl = bannerImgUrl;
		this.directoryUrl = directoryUrl;
		this.rating = rating;
		this.activatedBy = activatedBy;
		this.activatedOn = activatedOn;
		this.deactivatedBy = deactivatedBy;
		this.deactivatedOn = deactivatedOn;
		this.status = status;
		this.statusChangedBy = statusChangedBy;
		this.statusChangedOn = statusChangedOn;
		this.isDeleted = isDeleted;
		this.isGst = isGst;
		this.gstPercentage = gstPercentage;
		this.deliveryCharges = deliveryCharges;
		this.contactNo2 = contactNo2;
		this.contactNo3 = contactNo3;
		this.contactNo4 = contactNo4;
		this.deliveryTime = deliveryTime;
		this.discount = discount;
	}




	public Integer getDeliveryTime() {
		return deliveryTime;
	}




	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}




	public Double getDiscount() {
		return discount;
	}




	public void setDiscount(Double discount) {
		this.discount = discount;
	}




	public CommonResturants(String label, String address, String contactNo, String email, long countryId,
			long provinceId, long cityId, double latitude, double longitude, double accuracy, int isActive,
			String logoImgUrl, String profileImgUrl, String bannerImgUrl, String directoryUrl, double rating,
			Long activatedBy, Date activatedOn, Long deactivatedBy, Date deactivatedOn, Integer status,
			Long statusChangedBy, Date statusChangedOn, int isDeleted, int isGst, Double gstPercentage,
			Double deliveryCharges, String contactNo2, String contactNo3, String contactNo4, Integer deliveryTime,
			Double discount) {
		super();
		this.label = label;
		this.address = address;
		this.contactNo = contactNo;
		this.email = email;
		this.countryId = countryId;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.isActive = isActive;
		this.logoImgUrl = logoImgUrl;
		this.profileImgUrl = profileImgUrl;
		this.bannerImgUrl = bannerImgUrl;
		this.directoryUrl = directoryUrl;
		this.rating = rating;
		this.activatedBy = activatedBy;
		this.activatedOn = activatedOn;
		this.deactivatedBy = deactivatedBy;
		this.deactivatedOn = deactivatedOn;
		this.status = status;
		this.statusChangedBy = statusChangedBy;
		this.statusChangedOn = statusChangedOn;
		this.isDeleted = isDeleted;
		this.isGst = isGst;
		this.gstPercentage = gstPercentage;
		this.deliveryCharges = deliveryCharges;
		this.contactNo2 = contactNo2;
		this.contactNo3 = contactNo3;
		this.contactNo4 = contactNo4;
		this.deliveryTime = deliveryTime;
		this.discount = discount;
	}




	@Override
	public String toString() {
		return "CommonResturants [id=" + id + ", label=" + label + ", address=" + address + ", contactNo=" + contactNo
				+ ", email=" + email + ", countryId=" + countryId + ", provinceId=" + provinceId + ", cityId=" + cityId
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", accuracy=" + accuracy + ", isActive="
				+ isActive + ", logoImgUrl=" + logoImgUrl + ", profileImgUrl=" + profileImgUrl + ", bannerImgUrl="
				+ bannerImgUrl + ", directoryUrl=" + directoryUrl + ", rating=" + rating + ", activatedBy="
				+ activatedBy + ", activatedOn=" + activatedOn + ", deactivatedBy=" + deactivatedBy + ", deactivatedOn="
				+ deactivatedOn + ", status=" + status + ", statusChangedBy=" + statusChangedBy + ", statusChangedOn="
				+ statusChangedOn + ", isDeleted=" + isDeleted + ", isGst=" + isGst + ", gstPercentage="
				+ gstPercentage + ", deliveryCharges=" + deliveryCharges + "]";
	}




	public CommonResturants() {
		super();
	}
	
	
	
	
	
	
	
	 
	
}
