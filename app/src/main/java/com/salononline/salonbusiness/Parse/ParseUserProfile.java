package com.salononline.salonbusiness.Parse;

public class ParseUserProfile {
    private String userUuid,userFullName,userMobile,userType;

    public ParseUserProfile() {
    }

    public ParseUserProfile(String userUuid, String userFullName, String userMobile, String userType) {
        this.userUuid = userUuid;
        this.userFullName = userFullName;
        this.userMobile = userMobile;
        this.userType = userType;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
