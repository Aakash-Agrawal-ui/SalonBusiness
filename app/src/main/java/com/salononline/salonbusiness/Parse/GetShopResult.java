package com.salononline.salonbusiness.Parse;

public class GetShopResult {
    int code;
    ParseGetSalon data;

    public GetShopResult() {
    }

    public GetShopResult(int code, ParseGetSalon data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ParseGetSalon getData() {
        return data;
    }

    public void setData(ParseGetSalon data) {
        this.data = data;
    }
}
