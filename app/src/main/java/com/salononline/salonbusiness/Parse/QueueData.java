package com.salononline.salonbusiness.Parse;

public class QueueData {
    private String personUuid;

    public QueueData() {
    }

    public QueueData(String personUuid) {
        this.personUuid = personUuid;
    }

    public String getPersonUuid() {
        return personUuid;
    }

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }
}
