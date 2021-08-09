package com.salononline.salonbusiness.Parse;

public class ParseGetService {
    String shopUuid, serviceUuid, serviceName, serviceImage;
    int price;
    double discountedPrice;
    int discount;
    String serviceType, minServiceTime, shortDescription;

    public ParseGetService() {
    }

    public ParseGetService(String shopUuid, String serviceUuid, String serviceName, String serviceImage, int price, double discountedPrice, int discount, String serviceType, String minServiceTime, String shortDescription) {
        this.shopUuid = shopUuid;
        this.serviceUuid = serviceUuid;
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.discount = discount;
        this.serviceType = serviceType;
        this.minServiceTime = minServiceTime;
        this.shortDescription = shortDescription;
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

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMinServiceTime() {
        return minServiceTime;
    }

    public void setMinServiceTime(String minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
