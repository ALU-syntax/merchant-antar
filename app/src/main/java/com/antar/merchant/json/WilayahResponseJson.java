package com.antar.merchant.json;

import com.antar.merchant.models.Wilayah;

import java.util.ArrayList;
import java.util.List;

public class WilayahResponseJson {
    private String resultcode;
    private List<Wilayah> data = new ArrayList<>();

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public List<Wilayah> getData() {
        return data;
    }

    public void setData(List<Wilayah> data) {
        this.data = data;
    }
}
