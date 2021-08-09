package com.salononline.salonbusiness.Parse;

import java.util.List;

public class GetQueueNameResult {
    int code;
    GetQueueNames data;

    public GetQueueNameResult() {
    }

    public GetQueueNameResult(int code, GetQueueNames data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public GetQueueNames getData() {
        return data;
    }

    public void setData(GetQueueNames data) {
        this.data = data;
    }
}
