package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseGetAllBarberResult {
    private int code;
    private List<ParseAllBarbersData> data;
    private String message;

    public ParseGetAllBarberResult() {
    }

    public ParseGetAllBarberResult(int code, List<ParseAllBarbersData> data, String message) {
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

    public List<ParseAllBarbersData> getData() {
        return data;
    }

    public void setData(List<ParseAllBarbersData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
