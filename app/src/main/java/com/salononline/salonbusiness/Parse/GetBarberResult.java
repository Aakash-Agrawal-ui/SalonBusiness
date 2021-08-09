package com.salononline.salonbusiness.Parse;

public class GetBarberResult {
    int code;
    ParseGetBarber data;
    String message;

    public GetBarberResult() {
    }

    public GetBarberResult(int code, ParseGetBarber data, String message) {
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

    public ParseGetBarber getData() {
        return data;
    }

    public void setData(ParseGetBarber data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
