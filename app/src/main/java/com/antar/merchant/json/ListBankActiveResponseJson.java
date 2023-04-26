package com.antar.merchant.json;

import com.antar.merchant.models.BankModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 27/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class ListBankActiveResponseJson {

    @Expose
    @SerializedName("data")
    private List<BankModel> bankModel;

    public List<BankModel> getBankModel() {
        return bankModel;
    }

    public void setBankModel(List<BankModel> bankModel) {
        this.bankModel = bankModel;
    }
}
