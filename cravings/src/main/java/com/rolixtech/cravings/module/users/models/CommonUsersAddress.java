package com.rolixtech.cravings.module.users.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CommonUsersAddress {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_id")
	private long userId;
	private Long countryId;
	private Long provinceId;
	private Long cityId;
	private String address;
	private String postalCode;
	private Double lat;
	private Double lng;
	private Double accuracy;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	
	
	public CommonUsersAddress() {
		super();
	}
	public CommonUsersAddress(long userId, Long countryId, Long provinceId, Long cityId, String address,
			String postalCode, Double Lat, Double Lng, Double accuracy) {
		super();
		this.userId = userId;
		this.countryId = countryId;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.address = address;
		this.postalCode = postalCode;
		this.lat = lat;
		this.lng = lng;
		this.accuracy = accuracy;
		
	}
	public CommonUsersAddress(long id, long userId, Long countryId, Long provinceId, Long cityId, String address,
			String postalCode, Double lat, Double lng, Double accuracy) {
		super();
		this.id = id;
		this.userId = userId;
		this.countryId = countryId;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.address = address;
		this.postalCode = postalCode;
		this.lat = lat;
		this.lng = lng;
		this.accuracy = accuracy; 
	
	}
	
	
	public CommonUsersAddress(long userId, String address) {
		super();
		this.userId = userId;
		this.address = address;
	}
	@Override
	public String toString() {
		return "CommonUsersAddress [id=" + id + ", userId=" + userId + ", countryId=" + countryId + ", provinceId="
				+ provinceId + ", cityId=" + cityId + ", address=" + address + ", postalCode=" + postalCode + ", lat="
				+ lat + ", lng=" + lng + ", accuracy=" + accuracy + "]";
	}
	
	
	
	
	
	 
	
}
