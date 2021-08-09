package com.salononline.salonbusiness.Data;

public class StateItem {
    private String state_name,state_code;

    public StateItem(String state_name) {
        this.state_name = state_name;
    }

    public StateItem(String state_name, String state_code) {
        this.state_name = state_name;
        this.state_code = state_code;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }
}
