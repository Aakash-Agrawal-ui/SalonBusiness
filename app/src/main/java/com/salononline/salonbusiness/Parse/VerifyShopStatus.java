package com.salononline.salonbusiness.Parse;

public class VerifyShopStatus {
    int code;
    VerifyShop data;

    public VerifyShopStatus() {
    }

    public VerifyShopStatus(int code, VerifyShop data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public VerifyShop getData() {
        return data;
    }

    public void setData(VerifyShop data) {
        this.data = data;
    }
}
