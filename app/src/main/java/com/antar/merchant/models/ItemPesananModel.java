package com.antar.merchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ItemPesananModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("nama_item")
    private String nama_item;

    @Expose
    @SerializedName("jumlah_item")
    private String jumlah_item;

    @Expose
    @SerializedName("catatan_item")
    private String catatan;

    @Expose
    @SerializedName("total_harga")
    private String totalharga;


    public String getNama_item() {
        return nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public String getJumlah_item() {
        return jumlah_item;
    }

    public void setJumlah_item(String jumlah_item) {
        this.jumlah_item = jumlah_item;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(String totalharga) {
        this.totalharga = totalharga;
    }


}
