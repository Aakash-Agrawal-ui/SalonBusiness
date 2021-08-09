package com.salononline.salonbusiness.Parse;

public class ParseAllServicesData {
    private String shopUuid,serviceUuid,serviceName,shortDescription,minServiceTime;
    private  Integer price;
    private double discountedPrice;
    private Integer discount;
    private String serviceType,serviceState;

    public ParseAllServicesData() {
    }

    public ParseAllServicesData(String shopUuid, String serviceUuid, String serviceName, String shortDescription, String minServiceTime, Integer price, double discountedPrice, Integer discount, String serviceType, String serviceState) {
        this.shopUuid = shopUuid;
        this.serviceUuid = serviceUuid;
        this.serviceName = serviceName;
        this.shortDescription = shortDescription;
        this.minServiceTime = minServiceTime;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.discount = discount;
        this.serviceType = serviceType;
        this.serviceState = serviceState;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
    }

    public String getServiceUuid() {
        return serviceUuid;
    }

    public void setServiceUuid(String serviceUuid) {
        this.serviceUuid = serviceUuid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getMinServiceTime() {
        return minServiceTime;
    }

    public void setMinServiceTime(String minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceState() {
        return serviceState;
    }

    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }
}
