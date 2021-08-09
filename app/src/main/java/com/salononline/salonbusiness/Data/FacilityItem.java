package com.salononline.salonbusiness.Data;

import java.io.Serializable;

public class FacilityItem implements Serializable {
    private String facility_name;

    public FacilityItem() {
    }

    public FacilityItem(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }
}
