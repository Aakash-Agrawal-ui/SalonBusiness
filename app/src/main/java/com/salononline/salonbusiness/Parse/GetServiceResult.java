package com.salononline.salonbusiness.Parse;

public class GetServiceResult
{
    int code;
    ParseGetService data;
    String message;

    public GetServiceResult() {
    }

    public GetServiceResult(int code, ParseGetService data, String message) {
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

    public ParseGetService getData() {
        return data;
    }

    public void setData(ParseGetService data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
