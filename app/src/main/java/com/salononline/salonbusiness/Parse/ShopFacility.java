package com.salononline.salonbusiness.Parse;

public class ShopFacility {
    private boolean acAvailable;
    private boolean carParking;
    private boolean bikeParking;
    private boolean onlinePayment;
    private boolean homeService;
    private boolean cardPayment;
    private boolean hotWater;
    private boolean music;



    public ShopFacility() {
    }


    public ShopFacility(boolean acAvailable, boolean carParking, boolean bikeParking, boolean onlinePayment,
                        boolean homeService, boolean cardPayment, boolean hotWater, boolean music) {
        this.acAvailable = acAvailable;
        this.carParking = carParking;
        this.bikeParking = bikeParking;
        this.onlinePayment = onlinePayment;
        this.homeService = homeService;
        this.cardPayment = cardPayment;
        this.hotWater = hotWater;
        this.music = music;
    }

    public boolean isAcAvailable() {
        return acAvailable;
    }

    public void setAcAvailable(boolean acAvailable) {
        this.acAvailable = acAvailable;
    }

    public boolean isCarParking() {
        return carParking;
    }

    public void setCarParking(boolean carParking) {
        this.carParking = carParking;
    }

    public boolean isBikeParking() {
        return bikeParking;
    }

    public void setBikeParking(boolean bikeParking) {
        this.bikeParking = bikeParking;
    }

    public boolean isOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(boolean onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public boolean isHomeService() {
        return homeService;
    }

    public void setHomeService(boolean homeService) {
        this.homeService = homeService;
    }

    public boolean isCardPayment() {
        return cardPayment;
    }

    public void setCardPayment(boolean cardPayment) {
        this.cardPayment = cardPayment;
    }
    public boolean isHotWater() {
        return hotWater;
    }

    public void setHotWater(boolean hotWater) {
        this.hotWater = hotWater;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }
}
