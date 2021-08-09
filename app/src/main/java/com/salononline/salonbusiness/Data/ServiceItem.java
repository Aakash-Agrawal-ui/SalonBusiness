package com.salononline.salonbusiness.Data;

public class ServiceItem {
    private String shopUuid;
    private String serviceUuid;
    private String serviceName,shortDescription;
    private String minServiceTime;
    private Integer price;
    private Double discountedPrice;
    private Integer discount;
    private String serviceType,serviceState;

    public ServiceItem() {
    }

    public ServiceItem(String shopUuid, String serviceUuid, String serviceName, String shortDescription, String minServiceTime, Integer price, Double discountedPrice, Integer discount, String serviceType, String serviceState) {
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

    public void setDiscountedPrice(Double discountedPrice) {
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
