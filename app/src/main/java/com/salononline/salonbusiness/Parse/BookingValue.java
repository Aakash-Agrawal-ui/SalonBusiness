package com.salononline.salonbusiness.Parse;

import java.io.Serializable;

public class BookingValue implements Serializable {
    private String userUuid,shopUuid,bookingUuid;
    private Double bookingLatitude,bookingLongitude;
    private Integer totalServices,noOfPersons;
    private String createdAt,bookingStatus;

    public BookingValue() {
    }

    public BookingValue(String userUuid, String shopUuid, String bookingUuid, Double bookingLatitude, Double bookingLongitude, Integer totalServices, Integer noOfPersons, String createdAt, String bookingStatus) {
        this.userUuid = userUuid;
        this.shopUuid = shopUuid;
        this.bookingUuid = bookingUuid;
        this.bookingLatitude = bookingLatitude;
        this.bookingLongitude = bookingLongitude;
        this.totalServices = totalServices;
        this.noOfPersons = noOfPersons;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
    }

    public String getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(String bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public Double getBookingLatitude() {
        return bookingLatitude;
    }

    public void setBookingLatitude(Double bookingLatitude) {
        this.bookingLatitude = bookingLatitude;
    }

    public Double getBookingLongitude() {
        return bookingLongitude;
    }

    public void setBookingLongitude(Double bookingLongitude) {
        this.bookingLongitude = bookingLongitude;
    }

    public Integer getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(Integer totalServices) {
        this.totalServices = totalServices;
    }

    public Integer getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(Integer noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
