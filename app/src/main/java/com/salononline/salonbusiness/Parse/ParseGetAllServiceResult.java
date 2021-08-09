package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseGetAllServiceResult {
    private int code;
    private List<ParseAllServicesData> data;
    private String message;

    public ParseGetAllServiceResult() {
    }

    public ParseGetAllServiceResult(int code, List<ParseAllServicesData> data, String message) {
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

    public List<ParseAllServicesData> getData() {
        return data;
    }

    public void setData(List<ParseAllServicesData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
