package com.antar.merchant.utils.api.service;

import com.antar.merchant.json.ActivekatRequestJson;
import com.antar.merchant.json.AddEditItemRequestJson;
import com.antar.merchant.json.AddEditKategoriRequestJson;
import com.antar.merchant.json.BankResponseJson;
import com.antar.merchant.json.ChangePassRequestJson;
import com.antar.merchant.json.DetailRequestJson;
import com.antar.merchant.json.DetailTransResponseJson;
import com.antar.merchant.json.EditMerchantRequestJson;
import com.antar.merchant.json.EditProfileRequestJson;
import com.antar.merchant.json.FcmKeyResponseJson;
import com.antar.merchant.json.FcmRequestJson;
import com.antar.merchant.json.GetAyoPulsaBaseResponse;
import com.antar.merchant.json.GetFiturRequestJson;
import com.antar.merchant.json.GetFiturResponseJson;
import com.antar.merchant.json.GetOnRequestJson;
import com.antar.merchant.json.HistoryRequestJson;
import com.antar.merchant.json.HistoryResponseJson;
import com.antar.merchant.json.HomeRequestJson;
import com.antar.merchant.json.HomeResponseJson;
import com.antar.merchant.json.ItemRequestJson;
import com.antar.merchant.json.ItemResponseJson;
import com.antar.merchant.json.KategoriRequestJson;
import com.antar.merchant.json.KategoriResponseJson;
import com.antar.merchant.json.LoginRequestJson;
import com.antar.merchant.json.LoginResponseJson;
import com.antar.merchant.json.MobilePulsaHealthBPJSBaseResponse;
import com.antar.merchant.json.MobilePulsaPLNCheckResponse;
import com.antar.merchant.json.MobileTopUpPostPaidStatusJson;
import com.antar.merchant.json.MobileTopUpRequestModel;
import com.antar.merchant.json.MobileTopUpResponseModel;
import com.antar.merchant.json.PrivacyRequestJson;
import com.antar.merchant.json.PrivacyResponseJson;
import com.antar.merchant.json.RegisterRequestJson;
import com.antar.merchant.json.RegisterResponseJson;
import com.antar.merchant.json.ResponseJson;
import com.antar.merchant.json.TopUpAyoPulsaRequestJson;
import com.antar.merchant.json.TopUpAyoPulsaResponseModel;
import com.antar.merchant.json.TopUpBaseResponse;
import com.antar.merchant.json.TopUpRequestResponse;
import com.antar.merchant.json.TopUpTrackingResponseJson;
import com.antar.merchant.json.WalletRequestJson;
import com.antar.merchant.json.WalletResponseJson;
import com.antar.merchant.json.WilayahRequestJson;
import com.antar.merchant.json.WilayahResponseJson;
import com.antar.merchant.json.WithdrawRequestJson;
import com.antar.merchant.models.ayopulsa.PriceListDataModel;
import com.antar.merchant.models.ayopulsa.TopUpStatusModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface MerchantService {

    @POST("merchant/fcmkey")
    Call<FcmKeyResponseJson> fcmgetkey(@Body FcmRequestJson param);


    @POST("merchant/kategorimerchant")
    Call<GetFiturResponseJson> getFitur(@Body GetFiturRequestJson param);

    @POST("merchant/getwilayah")
    Call<WilayahResponseJson> getWilayah(@Body WilayahRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("merchant/kategorimerchantbyfitur")
    Call<GetFiturResponseJson> getKategori(@Body HistoryRequestJson param);

    @POST("merchant/onoff")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("merchant/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("merchant/register_merchant")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("merchant/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("merchant/edit_profile")
    Call<LoginResponseJson> editprofile(@Body EditProfileRequestJson param);

    @POST("merchant/edit_merchant")
    Call<LoginResponseJson> editmerchant(@Body EditMerchantRequestJson param);

    @POST("merchant/home")
    Call<HomeResponseJson> home(@Body HomeRequestJson param);

    @POST("merchant/history")
    Call<HistoryResponseJson> history(@Body HistoryRequestJson param);

    @POST("merchant/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("merchant/kategori")
    Call<KategoriResponseJson> kategori(@Body KategoriRequestJson param);

    @POST("merchant/item")
    Call<ItemResponseJson> itemlist(@Body ItemRequestJson param);

    @POST("merchant/active_kategori")
    Call<ResponseJson> activekategori(@Body ActivekatRequestJson param);

    @POST("merchant/active_item")
    Call<ResponseJson> activeitem(@Body ActivekatRequestJson param);

    @POST("merchant/add_kategori")
    Call<ResponseJson> addkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/edit_kategori")
    Call<ResponseJson> editkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/delete_kategori")
    Call<ResponseJson> deletekategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/add_item")
    Call<ResponseJson> additem(@Body AddEditItemRequestJson param);

    @POST("merchant/edit_item")
    Call<ResponseJson> edititem(@Body AddEditItemRequestJson param);

    @POST("merchant/delete_item")
    Call<ResponseJson> deleteitem(@Body AddEditItemRequestJson param);

    @POST("merchant/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("merchant/withdraw")
    Call<TopUpTrackingResponseJson> trackTopUp(@Body WithdrawRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("merchant/topupmidtrans")
    Call<ResponseJson> topupmidtrans(@Body WithdrawRequestJson param);

    @POST("merchant/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST()
    Call<MobileTopUpResponseModel> getAllMobileTopUpType(@Url String url, @Body MobileTopUpRequestModel param);

    @POST()
    Call<MobilePulsaPLNCheckResponse> checkMobilePulsaPlnSubscriber(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST("https://mobilepulsa.net/api/v1/bill/check")
    Call<MobilePulsaHealthBPJSBaseResponse> checkBPJSKesSubscriber(@Body MobileTopUpRequestModel param);

    @POST()
    Call<TopUpRequestResponse> requestTopUp(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST()
    Call<TopUpBaseResponse> requestTopUpWithBaseResponse(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST("https://mobilepulsa.net/api/v1/bill/check")
    Call<MobileTopUpPostPaidStatusJson> checkPostPaidStatus(@Body MobileTopUpRequestModel param);

    @GET("https://ayo-pesan.com/api/v1/prabayar/pulsa/operator")
    Call<GetAyoPulsaBaseResponse> getAyoPesanPulsaAndPaketDataPriceList(@Header("Authorization") String header);

    @GET("https://ayo-pesan.com/api/v1/prabayar/listrik/nominal")
    Call<PriceListDataModel> getAyoPesanPlnPriceList(@Header("Authorization") String header);

    @POST("https://ayo-pesan.com/api/v1/prabayar/pulsa/topup")
    Call<TopUpAyoPulsaResponseModel> ayoPesanTopUp(@Body TopUpAyoPulsaRequestJson params, @Header("Authorization") String header);

    @GET
    Call<TopUpStatusModel> checkAyoPesanStatusPayment(@Url String url, @Header("Authorization") String header);

}
