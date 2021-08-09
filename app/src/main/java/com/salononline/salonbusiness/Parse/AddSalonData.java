package com.salononline.salonbusiness.Parse;

import java.util.List;

public class AddSalonData {
    private String ownerName,shopName,shopMobile,shopEmail;
    private int seatsAvailable;
    private String logoImage,regCertLocation,msmeCertLocation;
    private String genderPrefer;
    private AddressDet address;
    private ShopFacility shopFacility;
    private List<ShopTimings> shopTimings;

    public AddSalonData() {
    }

    public AddSalonData(String ownerName, String shopName, String shopMobile, String shopEmail, int seatsAvailable, String logoImage, String regCertLocation, String msmeCertLocation
                        ,String genderPrefer, AddressDet address, ShopFacility shopFacility, List<ShopTimings> shopTimings) {
        this.ownerName = ownerName;
        this.shopName = shopName;
        this.shopMobile = shopMobile;
        this.shopEmail = shopEmail;
        this.seatsAvailable = seatsAvailable;
        this.logoImage = logoImage;
        this.regCertLocation = regCertLocation;
        this.msmeCertLocation = msmeCertLocation;
        this.genderPrefer = genderPrefer;
        this.address = address;
        this.shopFacility = shopFacility;
        this.shopTimings = shopTimings;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getRegCertLocation() {
        return regCertLocation;
    }

    public void setRegCertLocation(String regCertLocation) {
        this.regCertLocation = regCertLocation;
    }

    public String getMsmeCertLocation() {
        return msmeCertLocation;
    }

    public void setMsmeCertLocation(String msmeCertLocation) {
        this.msmeCertLocation = msmeCertLocation;
    }

    public String getGenderPrefer() {
        return genderPrefer;
    }

    public void setGenderPrefer(String genderPrefer) {
        this.genderPrefer = genderPrefer;
    }

    public AddressDet getAddress() {
        return address;
    }

    public void setAddress(AddressDet address) {
        this.address = address;
    }

    public ShopFacility getShopFacility() {
        return shopFacility;
    }

    public void setShopFacility(ShopFacility shopFacility) {
        this.shopFacility = shopFacility;
    }

    public List<ShopTimings> getShopTimings() {
        return shopTimings;
    }

    public void setShopTimings(List<ShopTimings> shopTimings) {
        this.shopTimings = shopTimings;
    }
}
