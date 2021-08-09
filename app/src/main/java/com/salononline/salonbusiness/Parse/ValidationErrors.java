package com.salononline.salonbusiness.Parse;

public class ValidationErrors
{
    String fieldName,message;

    public ValidationErrors(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public ValidationErrors() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
