package com.antar.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.antar.merchant.models.TransaksiMerchantModel;
import com.antar.merchant.models.User;

import java.util.ArrayList;
import java.util.List;

public class HomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("currency_text")
    @Expose
    private String currency_text;

    @SerializedName("app_aboutus")
    @Expose
    private String aboutus;

    @SerializedName("app_email")
    @Expose
    private String email;

    @SerializedName("app_contact")
    @Expose
    private String phone;

    @SerializedName("app_website")
    @Expose
    private String website;

    @SerializedName("mobilepulsa_username")
    @Expose
    private String mobilepulsausername;

    @SerializedName("mobilepulsa_api_key")
    @Expose
    private String mobilepulsaapikey;

    @SerializedName("mp_status")
    @Expose
    private String mpstatus;

    @SerializedName("mp_active")
    @Expose
    private String mpactive;

    @SerializedName("api_password")
    @Expose
    private String ayoPesanApiPassword;

    @SerializedName("api_token")
    @Expose
    private String ayoPesanApiToken;

    @SerializedName("harga_pulsa")
    @Expose
    private String hargaPulsa;

    public String getHargaPulsa() {
        return hargaPulsa;
    }

    public void setHargaPulsa(String hargaPulsa) {
        this.hargaPulsa = hargaPulsa;
    }

    @SerializedName("data")
    @Expose
    private List<TransaksiMerchantModel> data = new ArrayList<>();

    @SerializedName("user")
    @Expose
    private List<User> user = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransaksiMerchantModel> getData() {
        return data;
    }

    public void setData(List<TransaksiMerchantModel> data) {
        this.data = data;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencytext() {
        return currency_text;
    }

    public void setCurrencytext(String currencytext) {
        this.currency_text = currencytext;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getMobilepulsausername() {
        return mobilepulsausername;
    }

    public void setMobilepulsausername(String mobilepulsausername) {
        this.mobilepulsausername = mobilepulsausername;
    }
    public String getMobilepulsaapikey() {
        return mobilepulsaapikey;
    }

    public void setMobilepulsaapikey(String mobilepulsaapikey) {
        this.mobilepulsaapikey = mobilepulsaapikey;
    }
    public String getMpstatus() {
        return mpstatus;
    }

    public void setMpstatus(String mpstatus) {
        this.mpstatus = mpstatus;
    }

    public String getMpactive() {
        return mpactive;
    }

    public void setMpactive(String mpactive) {
        this.mpactive = mpactive;
    }

    public String getAyoPesanApiPassword() {
        return ayoPesanApiPassword;
    }

    public void setAyoPesanApiPassword(String ayoPesanApiPassword) {
        this.ayoPesanApiPassword = ayoPesanApiPassword;
    }

    public String getAyoPesanApiToken() {
        return ayoPesanApiToken;
    }

    public void setAyoPesanApiToken(String ayoPesanApiToken) {
        this.ayoPesanApiToken = ayoPesanApiToken;
    }
}
