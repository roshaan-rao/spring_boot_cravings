package com.rolixtech.cravings.module.generic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonSmsLog {

	 @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "id")
	private long id;
	private long smsLogTypeId;
	private long otp;
	private String sentTo;
	private Date sentOn;
	private Integer transactionId;
	private String responseCode;
	private long documentId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSmsLogTypeId() {
		return smsLogTypeId;
	}
	public void setSmsLogTypeId(long smsLogTypeId) {
		this.smsLogTypeId = smsLogTypeId;
	}
	public long getOtp() {
		return otp;
	}
	public void setOtp(long otp) {
		this.otp = otp;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	public Date getSentOn() {
		return sentOn;
	}
	public void setSentOn(Date sentOn) {
		this.sentOn = sentOn;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	@Override
	public String toString() {
		return "CommonSmsLog [id=" + id + ", smsLogTypeId=" + smsLogTypeId + ", otp=" + otp + ", sentTo=" + sentTo
				+ ", sentOn=" + sentOn + ", transactionId=" + transactionId + ", responseCode=" + responseCode
				+ ", documentId=" + documentId + "]";
	}
	
	
	
	
	
	
	


}