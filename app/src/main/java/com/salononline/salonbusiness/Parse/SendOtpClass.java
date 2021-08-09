package com.salononline.salonbusiness.Parse;

public class SendOtpClass {
    String username,otpEvent;

    public SendOtpClass() {
    }

    public SendOtpClass(String username, String otpEvent) {
        this.username = username;
        this.otpEvent = otpEvent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtpEvent() {
        return otpEvent;
    }

    public void setOtpEvent(String otpEvent) {
        this.otpEvent = otpEvent;
    }
}
