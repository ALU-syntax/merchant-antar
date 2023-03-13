package com.bumdesku.merchant.utils.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumdesku.merchant.constants.BaseApp;
import com.bumdesku.merchant.constants.Constants;
import com.bumdesku.merchant.json.MobilePulsaHealthBPJSBaseResponse;
import com.bumdesku.merchant.json.MobilePulsaPLNCheckResponse;
import com.bumdesku.merchant.json.MobileTopUpPostPaidStatusJson;
import com.bumdesku.merchant.json.MobileTopUpRequestModel;
import com.bumdesku.merchant.json.MobileTopUpResponseModel;
import com.bumdesku.merchant.json.TopUpBaseResponse;
import com.bumdesku.merchant.json.TopUpTrackingResponseJson;
import com.bumdesku.merchant.json.WithdrawRequestJson;
import com.bumdesku.merchant.models.User;
import com.bumdesku.merchant.utils.ProjectUtils;
import com.bumdesku.merchant.utils.SettingPreference;
import com.bumdesku.merchant.utils.api.service.ApiListener;
import com.bumdesku.merchant.utils.api.service.MerchantService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobilePulsaApiHelper {
    public static Call<MobileTopUpResponseModel> getMobilePulsaPriceList(String url, String sign, String command, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(sign);
        request.setCommand(command);
        request.setStatus("all");
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.getAllMobileTopUpType(url, request);
    }

    public static Call<MobilePulsaPLNCheckResponse> checkPLNSubscriber(String url, String sign, String command, String hp,
                                                                       SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(sign);
        request.setCommand(command);
        request.setDestinationPhoneNumber(hp);
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.checkMobilePulsaPlnSubscriber(url, request);
    }

    public static Call<MobilePulsaHealthBPJSBaseResponse> checkHealthBPJS(String destinationNumber, int numberMonthToPay,
                                                                          SettingPreference sp) {
        String refId = ProjectUtils.createTransactionID();
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCode("BPJS");
        request.setCommand("inq-pasca");
        request.setOrderId(refId);
        request.setMonth(String.valueOf(numberMonthToPay));
        request.setDestinationPhoneNumber(destinationNumber);
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.checkBPJSKesSubscriber(request);
    }

    public static Call<MobilePulsaHealthBPJSBaseResponse> payBPJSKes(String inquiryId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(inquiryId,sp)));
        request.setTransferId(inquiryId);
        request.setCommand("pay-pasca");
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.checkBPJSKesSubscriber(request);
    }

    public static Call<TopUpBaseResponse> topUpRequestToken(String hp, String pulsaCode, SettingPreference sp) {
        String refId = ProjectUtils.createTransactionID();
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);

        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCommand("topup");
        request.setDestinationPhoneNumber(hp);
        request.setPhoneCreditCode(pulsaCode);
        request.setOrderId(refId);
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.requestTopUpWithBaseResponse(Constants.MOBILEPULSA_PRODUCTION_URL, request);
    }

    public static Call<TopUpBaseResponse> checkPrepaidStatus(String refId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCommand("inquiry");
        request.setOrderId(refId);
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.requestTopUpWithBaseResponse(Constants.MOBILEPULSA_PRODUCTION_URL, request);
    }

    public static Call<MobileTopUpPostPaidStatusJson> checkPostPaidStatus(String refId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa("cs",sp)));
        request.setCommand("checkstatus");
        request.setOrderId(refId);
        MerchantService service = ServiceGenerator.createService(MerchantService.class);
        return service.checkPostPaidStatus(request);
    }

    public static void trackUserTopUp(String totalAmount, String operator, String idNumber, Context context, SettingPreference sp,
                                      ApiListener listener) {
        final User user = BaseApp.getInstance(context).getLoginUser();

        int amount = Integer.parseInt(totalAmount);
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(operator);
        request.setCard(idNumber);
        request.setName(user.getNamamitra());
        request.setAmount(String.valueOf(amount).replace(".", "").replace(sp.getSetting()[0], ""));
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType("withdraw");

        MerchantService service = ServiceGenerator.createService(MerchantService.class, user.getNoTelepon(), user.getPassword());
        service.trackTopUp(request).enqueue(new Callback<TopUpTrackingResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<TopUpTrackingResponseJson> call, @NonNull Response<TopUpTrackingResponseJson> response) {
                System.out.println(response.body()+ "ERRROR");
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopUpTrackingResponseJson> call, @NonNull Throwable t) {
                listener.onError();
            }
        });
    }
}
