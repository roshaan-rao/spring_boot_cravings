package com.rolixtech.cravings.module.users.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class RiderAssignDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String riderName;
    private Long remarksAddedBy;
    private String remarks;
    private Date remarksAddedOn;
    private Long orderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }




    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Date getRemarksAddedOn() {
        return remarksAddedOn;
    }

    public void setRemarksAddedOn(Date remarksAddedOn) {
        this.remarksAddedOn = remarksAddedOn;
    }

    public Long getRemarksAddedBy() {
        return remarksAddedBy;
    }

    public void setRemarksAddedBy(Long remarksAddedBy) {
        this.remarksAddedBy = remarksAddedBy;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }


}
