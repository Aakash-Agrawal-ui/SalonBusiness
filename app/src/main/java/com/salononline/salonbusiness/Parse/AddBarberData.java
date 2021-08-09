package com.salononline.salonbusiness.Parse;

public class AddBarberData {
    private String barberName,gender;

    public AddBarberData() {
    }

    public AddBarberData(String barberName, String gender) {
        this.barberName = barberName;
        this.gender = gender;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
