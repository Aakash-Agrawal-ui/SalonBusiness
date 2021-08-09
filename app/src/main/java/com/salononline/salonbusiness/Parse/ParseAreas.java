package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseAreas {
    private int code;
    private List<AreaFields> data;
    private String message;
    private boolean error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AreaFields> getData() {
        return data;
    }

    public void setData(List<AreaFields> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
