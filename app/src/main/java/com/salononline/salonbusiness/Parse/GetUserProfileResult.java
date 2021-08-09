package com.salononline.salonbusiness.Parse;

public class GetUserProfileResult {
    int code;
    ParseUserProfile data;

    public GetUserProfileResult() {
    }

    public GetUserProfileResult(int code, ParseUserProfile data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ParseUserProfile getData() {
        return data;
    }

    public void setData(ParseUserProfile data) {
        this.data = data;
    }
}
