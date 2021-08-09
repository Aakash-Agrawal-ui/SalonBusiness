package com.salononline.salonbusiness.Parse;

public class UpdateNumberData {
    private String userMobile,otpEvent,password;
    int otp;

    public UpdateNumberData() {
    }

    public UpdateNumberData(String userMobile, String otpEvent, String password, int otp) {
        this.userMobile = userMobile;
        this.otpEvent = otpEvent;
        this.password = password;
        this.otp = otp;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getOtpEvent() {
        return otpEvent;
    }

    public void setOtpEvent(String otpEvent) {
        this.otpEvent = otpEvent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
