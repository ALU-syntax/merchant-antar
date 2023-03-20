package com.antar.merchant.json;

import java.io.Serializable;
import java.util.List;

public class TopUpTrackingResponseJson implements Serializable {
    public String code;
    public String message;
    public List<Object> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
