package com.gtr.bmhr.Model;

public class AttendancePostClass {
    private Integer empId;
    private String Latitude;
    private String Longitude;
    private String LocationName;
    private String PicBack;
    private String PicFront;
    private String DeviceNo;

    public AttendancePostClass() {
    }

    public AttendancePostClass(Integer empId, String latitude, String longitude, String locationName, String picBack, String picFront, String deviceNo) {
        this.empId = empId;
        Latitude = latitude;
        Longitude = longitude;
        LocationName = locationName;
        PicBack = picBack;
        PicFront = picFront;
        DeviceNo = deviceNo;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getPicBack() {
        return PicBack;
    }

    public void setPicBack(String picBack) {
        PicBack = picBack;
    }

    public String getPicFront() {
        return PicFront;
    }

    public void setPicFront(String picFront) {
        PicFront = picFront;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }
}
