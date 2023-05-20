package com.rolixtech.cravings.module.users.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommonUsersRiderStatuses {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String statusStringValue;

    private String riderStatusValue;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusStringValue() {
        return statusStringValue;
    }

    public void setStatusStringValue(String statusStringValue) {
        this.statusStringValue = statusStringValue;
    }

    public String getRiderStatusValue() {
        return riderStatusValue;
    }

    public void setRiderStatusValue(String riderStatusValue) {
        this.riderStatusValue = riderStatusValue;
    }

    @Override
    public String toString() {
        return "CommonUsersRiderStatuses [id=" + id + ", statusStringValue=" + statusStringValue + "]";
    }

}
