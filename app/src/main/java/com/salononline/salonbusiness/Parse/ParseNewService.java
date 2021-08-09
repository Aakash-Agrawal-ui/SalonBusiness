package com.salononline.salonbusiness.Parse;

public class ParseNewService {
    String serviceName,serviceType;
    int price;
    Integer discount;
    String minServiceTime,serviceImage,shortDescription;

    public ParseNewService() {
    }

    public ParseNewService(String serviceName, String serviceType, int price, Integer discount,
                           String minServiceTime, String serviceImage, String shortDescription) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.price = price;
        this.discount = discount;
        this.minServiceTime = minServiceTime;
        this.serviceImage = serviceImage;
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

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
