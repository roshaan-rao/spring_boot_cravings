package com.rolixtech.cravings.module.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class ChangeStatusRemarks {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Long orderId;

    public Long getOrderStatusChangedBy() {
        return orderStatusChangedBy;
    }

    public void setOrderStatusChangedBy(Long orderStatusChangedBy) {
        this.orderStatusChangedBy = orderStatusChangedBy;
    }

    private Long orderStatusChangedBy;

    private Date orderStatusChangedOn;

    private String changedOrderStatus;

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

    public Date getOrderStatusChangedOn() {
        return orderStatusChangedOn;
    }

    public void setOrderStatusChangedOn(Date orderStatusChangedOn) {
        this.orderStatusChangedOn = orderStatusChangedOn;
    }

    public String getChangedOrderStatus() {
        return changedOrderStatus;
    }

    public void setChangedOrderStatus(String changedOrderStatus) {
        this.changedOrderStatus = changedOrderStatus;
    }
}
