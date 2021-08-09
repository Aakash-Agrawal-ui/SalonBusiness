package com.salononline.salonbusiness.Parse;

public class VerifyShop {
    private String shopApplicationNo;
    private boolean digitalVerified;

    public VerifyShop() {
    }

    public VerifyShop(String shopApplicationNo, boolean digitalVerified) {
        this.shopApplicationNo = shopApplicationNo;
        this.digitalVerified = digitalVerified;
    }

    public String getShopApplicationNo() {
        return shopApplicationNo;
    }

    public void setShopApplicationNo(String shopApplicationNo) {
        this.shopApplicationNo = shopApplicationNo;
    }

    public boolean isDigitalVerified() {
        return digitalVerified;
    }

    public void setDigitalVerified(boolean digitalVerified) {
        this.digitalVerified = digitalVerified;
    }
}
