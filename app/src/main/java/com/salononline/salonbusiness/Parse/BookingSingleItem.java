package com.salononline.salonbusiness.Parse;

import java.util.List;

public class BookingSingleItem {
    private String userUuid,shopUuid,bookingUuid;
    private Double bookingLatitude,bookingLongitude;
    private Integer totalServices,noOfPersons;
    private String createdAt,bookingStatus;
    private List<BookingPersons> bookingPersons;
    private List<BookingStages> bookingStages;
    private PriceDetails priceDetails;

    public BookingSingleItem() {
    }

    public BookingSingleItem(String userUuid, String shopUuid, String bookingUuid, Double bookingLatitude, Double bookingLongitude, Integer totalServices, Integer noOfPersons, String createdAt, String bookingStatus, List<BookingPersons> bookingPersons, List<BookingStages> bookingStages, PriceDetails priceDetails) {
        this.userUuid = userUuid;
        this.shopUuid = shopUuid;
        this.bookingUuid = bookingUuid;
        this.bookingLatitude = bookingLatitude;
        this.bookingLongitude = bookingLongitude;
        this.totalServices = totalServices;
        this.noOfPersons = noOfPersons;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
        this.bookingPersons = bookingPersons;
        this.bookingStages = bookingStages;
        this.priceDetails = priceDetails;
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

    public List<BookingPersons> getBookingPersons() {
        return bookingPersons;
    }

    public void setBookingPersons(List<BookingPersons> bookingPersons) {
        this.bookingPersons = bookingPersons;
    }

    public List<BookingStages> getBookingStages() {
        return bookingStages;
    }

    public void setBookingStages(List<BookingStages> bookingStages) {
        this.bookingStages = bookingStages;
    }

    public PriceDetails getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(PriceDetails priceDetails) {
        this.priceDetails = priceDetails;
    }
}
