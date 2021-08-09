package com.salononline.salonbusiness.Data;

public class BarberItem {
    private String barberUuid,shopUuid,barberName,gender,barberState;

    public BarberItem() {
    }

    public BarberItem(String barberUuid, String shopUuid, String barberName, String gender, String barberState) {
        this.barberUuid = barberUuid;
        this.shopUuid = shopUuid;
        this.barberName = barberName;
        this.gender = gender;
        this.barberState = barberState;
    }

    public String getBarberUuid() {
        return barberUuid;
    }

    public void setBarberUuid(String barberUuid) {
        this.barberUuid = barberUuid;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
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

    public String getBarberState() {
        return barberState;
    }

    public void setBarberState(String barberState) {
        this.barberState = barberState;
    }
}
