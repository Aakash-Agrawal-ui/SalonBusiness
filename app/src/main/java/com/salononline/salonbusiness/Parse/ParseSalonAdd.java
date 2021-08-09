package com.salononline.salonbusiness.Parse;

import java.util.List;

public class ParseSalonAdd {
    String message;
    ShopApplication data;
    int code;
    List<ValidationErrors> validationErrors;

    public ParseSalonAdd() {
    }

    public ParseSalonAdd(String message, ShopApplication data, int code, List<ValidationErrors> validationErrors) {
        this.message = message;
        this.data = data;
        this.code = code;
        this.validationErrors = validationErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ShopApplication getData() {
        return data;
    }

    public void setData(ShopApplication data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ValidationErrors> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationErrors> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
