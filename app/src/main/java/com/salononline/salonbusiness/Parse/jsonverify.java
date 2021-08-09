package com.salononline.salonbusiness.Parse;

public class jsonverify{
    String message;
    int code;
    boolean error;
    Data data;



    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
