package com.learn.spring.beans;

public class Employer {
    private long empId;
    private String empName;
    private String empLastName;
    private Long empAge;
    private String empPos;
    private long orgId;
    private String department;
    private Long salary;
    private String education;
    private boolean status;

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public Long getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Long empAge) {
        this.empAge = empAge;
    }

    public String getEmpPos() {
        return empPos;
    }

    public void setEmpPos(String empPos) {
        this.empPos = empPos;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
