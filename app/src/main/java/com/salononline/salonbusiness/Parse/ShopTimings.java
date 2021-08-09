package com.salononline.salonbusiness.Parse;

public class ShopTimings {
    private String day,openTime,closeTime;
    private boolean closed;

    public ShopTimings() {
    }

    public ShopTimings(String day, String openTime, String closeTime, boolean closed) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.closed = closed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
