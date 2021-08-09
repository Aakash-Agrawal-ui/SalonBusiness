package com.salononline.salonbusiness.Parse;

public class SignUpData {
    String userFullName,userMobile,password,confirmPassword;

    public SignUpData() {
    }

    public SignUpData(String userMobile, String password) {
        this.userMobile = userMobile;
        this.password = password;
    }

    public SignUpData(String userFullName, String userMobile, String password, String confirmPassword) {
        this.userFullName = userFullName;
        this.userMobile = userMobile;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
}
