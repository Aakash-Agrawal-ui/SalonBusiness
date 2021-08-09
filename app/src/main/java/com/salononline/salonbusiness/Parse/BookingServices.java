package com.salononline.salonbusiness.Parse;

import java.io.Serializable;

public class BookingServices implements Serializable {
    private String serviceUuid,serviceName,minServiceTime;
    private Double price,discountedPrice,discount;

    public BookingServices() {
    }

    public BookingServices(String serviceUuid, String serviceName, String minServiceTime, Double price, Double discountedPrice, Double discount) {
        this.serviceUuid = serviceUuid;
        this.serviceName = serviceName;
        this.minServiceTime = minServiceTime;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.discount = discount;
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

    public String getMinServiceTime() {
        return minServiceTime;
    }

    public void setMinServiceTime(String minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
