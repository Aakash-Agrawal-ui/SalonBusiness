package com.salononline.salonbusiness.Parse;

public class ParseShopStatus {
    int code;
    GetShopStatus data;

    public ParseShopStatus() {
    }

    public ParseShopStatus(int code, GetShopStatus data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public GetShopStatus getData() {
        return data;
    }

    public void setData(GetShopStatus data) {
        this.data = data;
    }
}
