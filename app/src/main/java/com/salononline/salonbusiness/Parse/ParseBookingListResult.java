package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseBookingListResult {
    private int code;
    private List<BookingSingleItem> data;
    private String message;

    public ParseBookingListResult() {
    }

    public ParseBookingListResult(int code, List<BookingSingleItem> data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BookingSingleItem> getData() {
        return data;
    }

    public void setData(List<BookingSingleItem> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
