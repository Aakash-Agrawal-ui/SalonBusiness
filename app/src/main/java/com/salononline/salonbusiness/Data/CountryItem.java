package com.salononline.salonbusiness.Data;

public class CountryItem {
    private String country_name,country_code;
    public CountryItem(String country_name) {
        this.country_name = country_name;
    }


    public CountryItem(String country_name, String country_code) {
        this.country_name = country_name;
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
