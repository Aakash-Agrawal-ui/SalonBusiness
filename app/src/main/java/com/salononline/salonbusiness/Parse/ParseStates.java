package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseStates {
    private int code;
    private List<StateFields> data;
    private String message;
    private boolean error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<StateFields> getData() {
        return data;
    }

    public void setData(List<StateFields> data) {
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
