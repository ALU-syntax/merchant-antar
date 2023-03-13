package com.bumdesku.merchant.utils.api.service;

import com.bumdesku.merchant.json.ActivekatRequestJson;
import com.bumdesku.merchant.json.AddEditItemRequestJson;
import com.bumdesku.merchant.json.AddEditKategoriRequestJson;
import com.bumdesku.merchant.json.BankResponseJson;
import com.bumdesku.merchant.json.ChangePassRequestJson;
import com.bumdesku.merchant.json.DetailRequestJson;
import com.bumdesku.merchant.json.DetailTransResponseJson;
import com.bumdesku.merchant.json.EditMerchantRequestJson;
import com.bumdesku.merchant.json.EditProfileRequestJson;
import com.bumdesku.merchant.json.FcmKeyResponseJson;
import com.bumdesku.merchant.json.FcmRequestJson;
import com.bumdesku.merchant.json.GetAyoPulsaBaseResponse;
import com.bumdesku.merchant.json.GetFiturRequestJson;
import com.bumdesku.merchant.json.GetFiturResponseJson;
import com.bumdesku.merchant.json.GetOnRequestJson;
import com.bumdesku.merchant.json.HistoryRequestJson;
import com.bumdesku.merchant.json.HistoryResponseJson;
import com.bumdesku.merchant.json.HomeRequestJson;
import com.bumdesku.merchant.json.HomeResponseJson;
import com.bumdesku.merchant.json.ItemRequestJson;
import com.bumdesku.merchant.json.ItemResponseJson;
import com.bumdesku.merchant.json.KategoriRequestJson;
import com.bumdesku.merchant.json.KategoriResponseJson;
import com.bumdesku.merchant.json.LoginRequestJson;
import com.bumdesku.merchant.json.LoginResponseJson;
import com.bumdesku.merchant.json.MobilePulsaHealthBPJSBaseResponse;
import com.bumdesku.merchant.json.MobilePulsaPLNCheckResponse;
import com.bumdesku.merchant.json.MobileTopUpPostPaidStatusJson;
import com.bumdesku.merchant.json.MobileTopUpRequestModel;
import com.bumdesku.merchant.json.MobileTopUpResponseModel;
import com.bumdesku.merchant.json.PrivacyRequestJson;
import com.bumdesku.merchant.json.PrivacyResponseJson;
import com.bumdesku.merchant.json.RegisterRequestJson;
import com.bumdesku.merchant.json.RegisterResponseJson;
import com.bumdesku.merchant.json.ResponseJson;
import com.bumdesku.merchant.json.TopUpAyoPulsaRequestJson;
import com.bumdesku.merchant.json.TopUpAyoPulsaResponseModel;
import com.bumdesku.merchant.json.TopUpBaseResponse;
import com.bumdesku.merchant.json.TopUpRequestResponse;
import com.bumdesku.merchant.json.TopUpTrackingResponseJson;
import com.bumdesku.merchant.json.WalletRequestJson;
import com.bumdesku.merchant.json.WalletResponseJson;
import com.bumdesku.merchant.json.WilayahRequestJson;
import com.bumdesku.merchant.json.WilayahResponseJson;
import com.bumdesku.merchant.json.WithdrawRequestJson;
import com.bumdesku.merchant.models.ayopulsa.PriceListDataModel;
import com.bumdesku.merchant.models.ayopulsa.TopUpStatusModel;

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
