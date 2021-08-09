package com.salononline.salonbusiness.Parse;

public class SignUpVeify {
    String userMobile,otpEvent;
    int otp;

    public SignUpVeify() {
    }

    public SignUpVeify(String userMobile, String otpEvent) {
        this.userMobile = userMobile;
        this.otpEvent = otpEvent;
    }

    public SignUpVeify(String userMobile, String otpEvent, int otp) {
        this.userMobile = userMobile;
        this.otpEvent = otpEvent;
        this.otp = otp;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUsername(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getOtpEvent() {
        return otpEvent;
    }

    public void setOtpEvent(String otpEvent) {
        this.otpEvent = otpEvent;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
