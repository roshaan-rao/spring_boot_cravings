package com.rolixtech.cravings.module.cravings.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CravingsPromotionalVouchersChangeLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Long recordId; 
	private String preFixStr;
	private String postFixStr;
	private String completeString;
	private int status;
	private Date createdOn;
	private long createdBy;
	private double amount;
	private double percentageVal;
	private Date validTo;
	private Date validFrom;
	private int isRedeemed;
	private Long logTypeId; 
	private String logReason;
	private long logCreatedBy;
	private Date logCreatedOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPreFixStr() {
		return preFixStr;
	}
	public void setPreFixStr(String preFixStr) {
		this.preFixStr = preFixStr;
	}
	public String getPostFixStr() {
		return postFixStr;
	}
	public void setPostFixStr(String postFixStr) {
		this.postFixStr = postFixStr;
	}
	public String getCompleteString() {
		return completeString;
	}
	public void setCompleteString(String completeString) {
		this.completeString = completeString;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPercentageVal() {
		return percentageVal;
	}
	public void setPercentageVal(double percentageVal) {
		this.percentageVal = percentageVal;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public int getIsRedeemed() {
		return isRedeemed;
	}
	public void setIsRedeemed(int isRedeemed) {
		this.isRedeemed = isRedeemed;
	}
	@Override
	public String toString() {
		return "CravingsPromotionalVouchers [id=" + id + ", preFixStr=" + preFixStr + ", postFixStr=" + postFixStr
				+ ", completeString=" + completeString + ", status=" + status + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", amount=" + amount + ", percentageVal=" + percentageVal + ", validTo="
				+ validTo + ", validFrom=" + validFrom + ", isRedeemed=" + isRedeemed + "]";
	}
	
	
}