package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseCategory {
    int code;
    List<CategoryData> data;
    String message;

    public ParseCategory() {
    }

    public ParseCategory(int code, List<CategoryData> data, String message) {
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

    public List<CategoryData> getData() {
        return data;
    }

    public void setData(List<CategoryData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
