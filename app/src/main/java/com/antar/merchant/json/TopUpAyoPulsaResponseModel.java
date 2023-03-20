package com.antar.merchant.json;

import com.antar.merchant.models.ayopulsa.TopUpDetailModel;

import java.io.Serializable;

public class TopUpAyoPulsaResponseModel implements Serializable {

    private String message;
    private TopUpDetailModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopUpDetailModel getData() {
        return data;
    }

    public void setData(TopUpDetailModel data) {
        this.data = data;
    }
}
