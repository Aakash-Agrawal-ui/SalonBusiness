package com.salononline.salonbusiness.Parse;

import java.io.Serializable;
import java.util.List;

public class BarberBookingItem implements Serializable {
    private String createdAt,barberUuid,shopUuid,barberName,gender;
    private Integer liveQueue,pendingQueue,totalQueueOfDay,totalDeclined,totalCanceled,totalCompleted;
    private List<BookingQueuePersons> bookingPersons;

    public BarberBookingItem() {
    }

    public BarberBookingItem(String createdAt, String barberUuid, String shopUuid, String barberName, String gender, Integer liveQueue, Integer pendingQueue, Integer totalQueueOfDay, Integer totalDeclined, Integer totalCanceled, Integer totalCompleted, List<BookingQueuePersons> bookingPersons) {
        this.createdAt = createdAt;
        this.barberUuid = barberUuid;
        this.shopUuid = shopUuid;
        this.barberName = barberName;
        this.gender = gender;
        this.liveQueue = liveQueue;
        this.pendingQueue = pendingQueue;
        this.totalQueueOfDay = totalQueueOfDay;
        this.totalDeclined = totalDeclined;
        this.totalCanceled = totalCanceled;
        this.totalCompleted = totalCompleted;
        this.bookingPersons = bookingPersons;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Integer getTotalQueueOfDay() {
        return totalQueueOfDay;
    }

    public void setTotalQueueOfDay(Integer totalQueueOfDay) {
        this.totalQueueOfDay = totalQueueOfDay;
    }

    public Integer getTotalDeclined() {
        return totalDeclined;
    }

    public void setTotalDeclined(Integer totalDeclined) {
        this.totalDeclined = totalDeclined;
    }

    public Integer getTotalCanceled() {
        return totalCanceled;
    }

    public void setTotalCanceled(Integer totalCanceled) {
        this.totalCanceled = totalCanceled;
    }

    public Integer getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(Integer totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public List<BookingQueuePersons> getBookingPersons() {
        return bookingPersons;
    }

    public void setBookingPersons(List<BookingQueuePersons> bookingPersons) {
        this.bookingPersons = bookingPersons;
    }
}
