package com.salononline.salonbusiness.Parse;

public class ParseGetQueueBarber {
    int code;
    BarberBookingItem data;

    public ParseGetQueueBarber() {
    }

    public ParseGetQueueBarber(int code, BarberBookingItem data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BarberBookingItem getData() {
        return data;
    }

    public void setData(BarberBookingItem data) {
        this.data = data;
    }
}
