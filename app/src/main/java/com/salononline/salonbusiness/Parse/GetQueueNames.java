package com.salononline.salonbusiness.Parse;


import java.util.List;

public class GetQueueNames {
    List<ServiceQueue> maleServiceQueue;
    List<ServiceQueue> femaleServiceQueue;

    public GetQueueNames() {
    }

    public GetQueueNames(List<ServiceQueue> maleServiceQueue, List<ServiceQueue> femaleServiceQueue) {
        this.maleServiceQueue = maleServiceQueue;
        this.femaleServiceQueue = femaleServiceQueue;
    }

    public List<ServiceQueue> getMaleServiceQueue() {
        return maleServiceQueue;
    }

    public void setMaleServiceQueue(List<ServiceQueue> maleServiceQueue) {
        this.maleServiceQueue = maleServiceQueue;
    }

    public List<ServiceQueue> getFemaleServiceQueue() {
        return femaleServiceQueue;
    }

    public void setFemaleServiceQueue(List<ServiceQueue> femaleServiceQueue) {
        this.femaleServiceQueue = femaleServiceQueue;
    }
}
