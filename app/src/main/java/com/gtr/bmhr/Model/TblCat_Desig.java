package com.gtr.bmhr.Model;

public class TblCat_Desig {
    private Integer desigId;
    private String desigName;

    public TblCat_Desig() {
    }

    public TblCat_Desig(Integer desigId, String desigName) {
        this.desigId = desigId;
        this.desigName = desigName;
    }

    public Integer getDesigId() {
        return desigId;
    }

    public void setDesigId(Integer desigId) {
        this.desigId = desigId;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName = desigName;
    }
}
