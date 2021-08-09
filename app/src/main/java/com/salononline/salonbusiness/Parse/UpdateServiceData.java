package com.salononline.salonbusiness.Parse;

public class UpdateServiceData {
    String serviceName, serviceType;
    int price;
    String serviceImage;
    Integer discount;
    String minServiceTime;
    String shortDescription;

    public UpdateServiceData() {
    }

    public UpdateServiceData(String serviceName, String serviceType, int price, String serviceImage, Integer discount, String minServiceTime, String shortDescription) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.price = price;
        this.serviceImage = serviceImage;
        this.discount = discount;
        this.minServiceTime = minServiceTime;
        this.shortDescription = shortDescription;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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
