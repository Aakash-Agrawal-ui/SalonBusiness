package com.salononline.salonbusiness.Parse;

public class BookingStages {
    private String status,message;

    public BookingStages() {
    }

    public BookingStages(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
