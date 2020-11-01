package com.gtr.bmhr.Model;

public class EmployeeInfo {
    private Integer empId;
    private Integer isMobileUser;
    private String empName;
    private String empCode;
    private Integer desigId;
    private TblCat_Desig tblCat_Desig;
    private String image;

    public EmployeeInfo() {
    }

    public EmployeeInfo(Integer empId, Integer isMobileUser, String empName, String empCode, Integer desigId, TblCat_Desig tblCat_Desig, String image) {
        this.empId = empId;
        this.isMobileUser = isMobileUser;
        this.empName = empName;
        this.empCode = empCode;
        this.desigId = desigId;
        this.tblCat_Desig = tblCat_Desig;
        this.image = image;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getIsMobileUser() {
        return isMobileUser;
    }

    public void setIsMobileUser(Integer isMobileUser) {
        this.isMobileUser = isMobileUser;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getDesigId() {
        return desigId;
    }

    public void setDesigId(Integer desigId) {
        this.desigId = desigId;
    }

    public TblCat_Desig getTblCat_Desig() {
        return tblCat_Desig;
    }

    public void setTblCat_Desig(TblCat_Desig tblCat_Desig) {
        this.tblCat_Desig = tblCat_Desig;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
