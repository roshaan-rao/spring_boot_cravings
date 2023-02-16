package com.rolixtech.cravings.module.cravings.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CravingsPromotionalVouchers {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String preFixStr;
	private String postFixStr;
	private String completeString;
	private int status;
	private Date createdOn;
	private long createdBy;
	private Date statusChangedOn;
	private Long statusChangeBy;
	private double amount;
	private double percentageVal;
	private Date validTo;
	private Date validFrom;
	private int isRedeemed;
	private long valueType;
	private int isGroup;
	private long voucherPurposeId;
	private long groupId;
	
	
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
	
	
	public Date getStatusChangedOn() {
		return statusChangedOn;
	}
	public void setStatusChangedOn(Date statusChangedOn) {
		this.statusChangedOn = statusChangedOn;
	}
	public Long getStatusChangeBy() {
		return statusChangeBy;
	}
	public void setStatusChangeBy(Long statusChangeBy) {
		this.statusChangeBy = statusChangeBy;
	}
	public long getValueType() {
		return valueType;
	}
	public void setValueType(long valueType) {
		this.valueType = valueType;
	}
	
	public int getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}
	public long getVoucherPurposeId() {
		return voucherPurposeId;
	}
	public void setVoucherPurposeId(long voucherPurposeId) {
		this.voucherPurposeId = voucherPurposeId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "CravingsPromotionalVouchers [id=" + id + ", preFixStr=" + preFixStr + ", postFixStr=" + postFixStr
				+ ", completeString=" + completeString + ", status=" + status + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", statusChangedOn=" + statusChangedOn + ", statusChangeBy="
				+ statusChangeBy + ", amount=" + amount + ", percentageVal=" + percentageVal + ", validTo=" + validTo
				+ ", validFrom=" + validFrom + ", isRedeemed=" + isRedeemed + ", valueType=" + valueType + ", isGroup="
				+ isGroup + ", voucherPurposeId=" + voucherPurposeId + ", groupId=" + groupId + "]";
	}
	
	
	
	
	
}
