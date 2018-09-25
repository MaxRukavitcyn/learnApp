package com.learn.spring.beans;


import java.sql.Timestamp;
import java.util.Date;


public class Organization {
    private long orgId;
    private String orgName;
    private String orgAdress;
    private String orgType;
    private String activities;
    private String director;
    private String departments;
    private boolean insalubrity;
    private Timestamp dateCreate;
    private boolean status;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAdress() {
        return orgAdress;
    }

    public void setOrgAdress(String orgAdress) {
        this.orgAdress = orgAdress;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public boolean isInsalubrity() {
        return insalubrity;
    }

    public void setInsalubrity(boolean insalubrity) {
        this.insalubrity = insalubrity;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
