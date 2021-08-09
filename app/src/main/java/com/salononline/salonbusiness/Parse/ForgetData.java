package com.salononline.salonbusiness.Parse;

public class ForgetData {
    String userMobile;
    String otpEvent;
    String password;
    String confirmPassword;
    int otp;

    public ForgetData() {
    }

    public ForgetData(String userMobile, String otpEvent, String password, String confirmPassword, int otp) {
        this.userMobile = userMobile;
        this.otpEvent = otpEvent;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}





