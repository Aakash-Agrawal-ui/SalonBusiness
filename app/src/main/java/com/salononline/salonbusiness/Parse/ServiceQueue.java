package com.salononline.salonbusiness.Parse;

public class ServiceQueue {
    private String barberName,barberUuid,gender;
    private Integer liveQueue,pendingQueue;
    private String nextAvailableAt;

    public ServiceQueue() {
    }

    public ServiceQueue(String barberName, String barberUuid, String gender, Integer liveQueue, Integer pendingQueue, String nextAvailableAt) {
        this.barberName = barberName;
        this.barberUuid = barberUuid;
        this.gender = gender;
        this.liveQueue = liveQueue;
        this.pendingQueue = pendingQueue;
        this.nextAvailableAt = nextAvailableAt;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getBarberUuid() {
        return barberUuid;
    }

    public void setBarberUuid(String barberUuid) {
        this.barberUuid = barberUuid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getLiveQueue() {
        return liveQueue;
    }

    public void setLiveQueue(Integer liveQueue) {
        this.liveQueue = liveQueue;
    }

    public Integer getPendingQueue() {
        return pendingQueue;
    }

    public void setPendingQueue(Integer pendingQueue) {
        this.pendingQueue = pendingQueue;
    }

    public String getNextAvailableAt() {
        return nextAvailableAt;
    }

    public void setNextAvailableAt(String nextAvailableAt) {
        this.nextAvailableAt = nextAvailableAt;
    }
}
