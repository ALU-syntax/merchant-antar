package com.bumdesku.merchant.utils.api;

import com.bumdesku.merchant.json.GetAyoPulsaBaseResponse;
import com.bumdesku.merchant.json.TopUpAyoPulsaRequestJson;
import com.bumdesku.merchant.json.TopUpAyoPulsaResponseModel;
import com.bumdesku.merchant.models.ayopulsa.PriceListDataModel;
import com.bumdesku.merchant.models.ayopulsa.TopUpStatusModel;
import com.bumdesku.merchant.utils.api.service.MerchantService;

import javax.inject.Singleton;
import retrofit2.Call;

public class AyoPulsaApiHelper {

    private static AyoPulsaApiHelper obj;
    private MerchantService MerchantService;
    private AyoPulsaApiHelper(){
        MerchantService = ServiceGenerator.createService(MerchantService.class);
    }

    public static AyoPulsaApiHelper getInstance() {
        if (obj == null) {
            synchronized (Singleton.class) {
                if (obj == null) {
                    obj = new AyoPulsaApiHelper();
                }
            }
        }
        return obj;
    }

    private String header;
    private String password;

    public void setHeader(String value) {
        header = value;
    }

    public void setPassword(String value) {
        password = value;
    }

    public Call<GetAyoPulsaBaseResponse> getAyoPesanPulsaPriceList() {
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.getAyoPesanPulsaAndPaketDataPriceList(header);
    }

    public Call<PriceListDataModel> getAyoPesanPlnPriceList() {
        return MerchantService.getAyoPesanPlnPriceList(header);
    }

    public Call<TopUpAyoPulsaResponseModel> topUpRequest(TopUpAyoPulsaRequestJson params) {
        params.setPassword(password);
        return MerchantService.ayoPesanTopUp(params, header);
    }

    public Call<TopUpStatusModel> checkAyoPesanPaymentStatus(String refId) {
        String url = "https://ayo-pesan.com/api/v1/prabayar/check-status/"+ refId;
        return MerchantService.checkAyoPesanStatusPayment(url, header);
    }
}
