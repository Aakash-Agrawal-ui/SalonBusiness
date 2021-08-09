package com.salononline.salonbusiness.Parse;

import java.io.Serializable;
import java.util.List;

public class BookingQueuePersons implements Serializable {
    private String userName,gender,userMobile;
    private List<BookingServices> bookingServices;
    private String barberName,barberUuid;
    private Double subTotal;
    private String totalServiceTime;
    private Integer noOfServices;
    private String queueStatus,servingTime,queueNumber,personUuid;
    private BookingValue booking;

    public BookingQueuePersons() {
    }

    public BookingQueuePersons(String userName, String gender, String userMobile, List<BookingServices> bookingServices, String barberName, String barberUuid, Double subTotal, String totalServiceTime, Integer noOfServices, String queueStatus, String servingTime, String queueNumber, String personUuid, BookingValue booking) {
        this.userName = userName;
        this.gender = gender;
        this.userMobile = userMobile;
        this.bookingServices = bookingServices;
        this.barberName = barberName;
        this.barberUuid = barberUuid;
        this.subTotal = subTotal;
        this.totalServiceTime = totalServiceTime;
        this.noOfServices = noOfServices;
        this.queueStatus = queueStatus;
        this.servingTime = servingTime;
        this.queueNumber = queueNumber;
        this.personUuid = personUuid;
        this.booking = booking;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public List<BookingServices> getBookingServices() {
        return bookingServices;
    }

    public void setBookingServices(List<BookingServices> bookingServices) {
        this.bookingServices = bookingServices;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getBarberUuid() {
        return barberUuid;
    }

    public void setBarberUuid(String barberUuid) {
        this.barberUuid = barberUuid;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotalServiceTime() {
        return totalServiceTime;
    }

    public void setTotalServiceTime(String totalServiceTime) {
        this.totalServiceTime = totalServiceTime;
    }

    public Integer getNoOfServices() {
        return noOfServices;
    }

    public void setNoOfServices(Integer noOfServices) {
        this.noOfServices = noOfServices;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getServingTime() {
        return servingTime;
    }

    public void setServingTime(String servingTime) {
        this.servingTime = servingTime;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getPersonUuid() {
        return personUuid;
    }

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

    public BookingValue getBooking() {
        return booking;
    }

    public void setBooking(BookingValue booking) {
        this.booking = booking;
    }
}
