package com.antar.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.antar.merchant.models.DriverModel;
import com.antar.merchant.models.ItemPesananModel;
import com.antar.merchant.models.PelangganModel;
import com.antar.merchant.models.TransaksiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class DetailTransResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<TransaksiModel> data = new ArrayList<>();

    @Expose
    @SerializedName("driver")
    private List<DriverModel> driver = new ArrayList<>();

    @Expose
    @SerializedName("pelanggan")
    private List<PelangganModel> pelanggan = new ArrayList<>();

    @Expose
    @SerializedName("item")
    private List<ItemPesananModel> item = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransaksiModel> getData() {
        return data;
    }

    public void setData(List<TransaksiModel> data) {
        this.data = data;
    }

    public List<DriverModel> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverModel> driver) {
        this.driver = driver;
    }

    public List<PelangganModel> getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(List<PelangganModel> pelanggan) {
        this.pelanggan = pelanggan;
    }

    public List<ItemPesananModel> getItem() {
        return item;
    }

    public void setItem(List<ItemPesananModel> item) {
        this.item = item;
    }
}
