package com.salononline.salonbusiness.Parse;

public class UpdateSalonDetailsData {
    private String ownerName,shopMobile,shopEmail;
    private ShopFacility shopFacility;

    public UpdateSalonDetailsData() {
    }

    public UpdateSalonDetailsData(String ownerName, String shopMobile, String shopEmail, ShopFacility shopFacility) {
        this.ownerName = ownerName;
        this.shopMobile = shopMobile;
        this.shopEmail = shopEmail;
        this.shopFacility = shopFacility;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public ShopFacility getShopFacility() {
        return shopFacility;
    }

    public void setShopFacility(ShopFacility shopFacility) {
        this.shopFacility = shopFacility;
    }
}
