package com.salononline.salonbusiness.Parse;

public class AddressDet {
    private String latitude,longitude,street,locality,pincode,cityName,stateName,countryName,areaCode,cityCode,stateCode,countryCode;

    public AddressDet() {
    }

    public AddressDet(String latitude, String longitude, String street, String locality, String pincode,
                      String cityName, String stateName, String countryName, String areaCode, String cityCode, String stateCode, String countryCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.locality = locality;
        this.pincode = pincode;
        this.cityName = cityName;
        this.stateName = stateName;
        this.countryName = countryName;
        this.areaCode = areaCode;
        this.cityCode = cityCode;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String country) {
        this.countryCode = country;
    }
}
