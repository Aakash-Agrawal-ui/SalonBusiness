package com.salononline.salonbusiness.Data;

public class AreaItem {
    private String area_name,area_code;

    public AreaItem() {
    }

    public AreaItem(String area_name) {
        this.area_name = area_name;
    }

    public AreaItem(String area_name, String area_code) {
        this.area_name = area_name;
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }
}
