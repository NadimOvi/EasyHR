package com.gtr.bmhr.Model;

public class AttendanceDataShow {
    private Integer aId;
    private Integer empId;
    private String dtPunchDate;
    private String dtPunchTime;
    private String locationName;

    public AttendanceDataShow() {
    }

    public AttendanceDataShow(Integer aId, Integer empId, String dtPunchDate, String dtPunchTime, String locationName) {
        this.aId = aId;
        this.empId = empId;
        this.dtPunchDate = dtPunchDate;
        this.dtPunchTime = dtPunchTime;
        this.locationName = locationName;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getDtPunchDate() {
        return dtPunchDate;
    }

    public void setDtPunchDate(String dtPunchDate) {
        this.dtPunchDate = dtPunchDate;
    }

    public String getDtPunchTime() {
        return dtPunchTime;
    }

    public void setDtPunchTime(String dtPunchTime) {
        this.dtPunchTime = dtPunchTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
