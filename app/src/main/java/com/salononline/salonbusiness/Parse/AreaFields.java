package com.salononline.salonbusiness.Parse;

public class AreaFields {
    private String areaName,areaCode;

    public AreaFields() {
    }

    public AreaFields(String areaName, String areaCode) {
        this.areaName = areaName;
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
