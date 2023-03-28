package com.antar.merchant.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.merchant.R;
import com.antar.merchant.constants.BaseApp;
import com.antar.merchant.constants.Constants;
import com.antar.merchant.item.TopUpPlnHistoryItem;
import com.antar.merchant.json.FcmKeyResponseJson;
import com.antar.merchant.json.FcmRequestJson;
import com.antar.merchant.json.MobilePulsaPLNCheckResponse;
import com.antar.merchant.json.MobileTopUpDetailResponseModel;
import com.antar.merchant.json.MobileTopUpResponseModel;
import com.antar.merchant.json.TopUpBaseResponse;
import com.antar.merchant.json.TopUpRequestResponse;
import com.antar.merchant.json.fcm.FCMMessage;
import com.antar.merchant.models.Notif;
import com.antar.merchant.models.User;
import com.antar.merchant.utils.LocaleHelper;
import com.antar.merchant.utils.ProjectUtils;
import com.antar.merchant.utils.SettingPreference;
import com.antar.merchant.utils.Utility;
import com.antar.merchant.utils.api.MobilePulsaApiHelper;
import com.antar.merchant.utils.api.FCMHelper;
import com.antar.merchant.utils.api.ServiceGenerator;
import com.antar.merchant.utils.api.service.ApiListener;
import com.antar.merchant.utils.api.service.MerchantService;
import com.antar.merchant.utils.local_interface.OnItemClicked;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUpPlnActivity extends AppCompatActivity implements OnItemClicked {
    RelativeLayout rlProgress,rlnotif;
    String disableBack;
    EditText etIdPln;
    Spinner spinner;
    TextView notif;
    Button submit;
    SettingPreference sp;
    ImageView ivBack;
    RecyclerView rcView;
    LinearLayout llListPastTopUp;
    List<TopUpRequestResponse> listOfPreviousPLNTopUpRequest= new ArrayList<>();
    List<MobileTopUpDetailResponseModel> dataList = new ArrayList<>();
    private String keyss;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_up_pln_activity);
        getkey();
        rlnotif = findViewById(R.id.rlnotif);

        rlProgress = findViewById(R.id.rlprogress);
        etIdPln = findViewById(R.id.etIdPln);
        spinner = findViewById(R.id.listNominal);
        notif = findViewById(R.id.textnotif);
        submit = findViewById(R.id.submit);
        ivBack = findViewById(R.id.ivBack);
        rcView = findViewById(R.id.rcPlnTopUpList);
        llListPastTopUp = findViewById(R.id.llListPastTopUp);

        sp = new SettingPreference(this);

        getMobilePulsaPriceListData();

        ivBack.setOnClickListener(v -> finish());

        submit.setOnClickListener(v -> {
            User userLogin = BaseApp.getInstance(TopUpPlnActivity.this).getLoginUser();
            if(userLogin.getWalletSaldo() < dataList.get(spinner.getSelectedItemPosition()).getPhoneCreditPrice()) {
                Toast.makeText(this, "Your wallet is not enough. Please top up first", Toast.LENGTH_LONG).show();
            } else {
                checkPlnSubscriber();
            }
        });
    }

    private void getMobilePulsaPriceListData() {
        progressShow();
        String url = Constants.MOBILEPULSA_PRODUCTION_URL + "/pln/pln";
        MobilePulsaApiHelper.getMobilePulsaPriceList( url,
                ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa("pl", sp)), "pricelist",sp).enqueue(new Callback<MobileTopUpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<MobileTopUpResponseModel> call, @NonNull Response<MobileTopUpResponseModel> response) {
               progressHide();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<String> categoryList = new ArrayList<>();
                        dataList.addAll(response.body().getDetailResponse());
                        Collections.sort(dataList, (o1, o2) -> Integer.valueOf(o1.getPhoneCreditDescription()).compareTo(Integer.valueOf(o2.getPhoneCreditDescription())));
                        for (MobileTopUpDetailResponseModel data: dataList) {
                            int price = data.getPhoneCreditPrice() + 600;
                            categoryList.add(Utility.convertCurrency(String.valueOf(price), TopUpPlnActivity.this));
                        }
                        initSpinner(categoryList);
                        initPastPLNPaidBill(sp.getSetting()[10]);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobileTopUpResponseModel> call, @NonNull Throwable t) {
                progressHide();
            }
        });
    }

    private void initPastPLNPaidBill(String list) {
        if (!list.isEmpty()) {
            llListPastTopUp.setVisibility(View.VISIBLE);
            Type listOfMyClassObject = new TypeToken<List<TopUpRequestResponse>>() {}.getType();
            listOfPreviousPLNTopUpRequest.addAll(new Gson().fromJson(list, listOfMyClassObject));

            rcView.setHasFixedSize(true);
            rcView.setNestedScrollingEnabled(false);
            rcView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            TopUpPlnHistoryItem item = new TopUpPlnHistoryItem(listOfPreviousPLNTopUpRequest, this);
            rcView.setAdapter(item);
        } else {
            llListPastTopUp.setVisibility(View.GONE);
        }
    }

    private void initSpinner(List<String> data) {
        ArrayAdapter<String> nominalListAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        nominalListAdapter.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);
        spinner.setAdapter(nominalListAdapter);
    }

    private void checkPlnSubscriber() {
        progressShow();
        String enteredMeter = etIdPln.getText().toString();
        MobilePulsaApiHelper.checkPLNSubscriber( Constants.MOBILEPULSA_PRODUCTION_URL, ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(enteredMeter,sp)),
                "inquiry_pln", enteredMeter, sp).enqueue(new Callback<MobilePulsaPLNCheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<MobilePulsaPLNCheckResponse> call, @NonNull Response<MobilePulsaPLNCheckResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if ("SUCCESS".equalsIgnoreCase(response.body().getDetailResponse().getMessage())) {
                            topUpRequest();
                        } else {
                            progressHide();
                            notif(response.body().getDetailResponse().getMessage());
                        }
                    } else {
                        progressHide();
                        notif("error, silahkan cek data akun anda!");
                    }
                } else {
                    notif("error, silahkan cek data akun anda!");
                    progressHide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobilePulsaPLNCheckResponse> call, @NonNull Throwable t) {
                progressHide();
                notif("error, silahkan cek data akun anda!");
            }
        });
    }

    private void topUpRequest() {
        String pulsaCode = dataList.get(spinner.getSelectedItemPosition()).getPhoneCreditCode();
        MobilePulsaApiHelper.topUpRequestToken(etIdPln.getText().toString(),
                pulsaCode,sp).enqueue(new Callback<TopUpBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopUpBaseResponse> call,@NonNull  Response<TopUpBaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sp.updatePlnList(response.body().getData());
                        trackTopUp();
                    } else {
                        progressHide();
                        notif(Objects.requireNonNull(response.errorBody()).toString());
                    }
                } else {
                    progressHide();
                    notif("error, silahkan cek data akun anda!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopUpBaseResponse> call, @NonNull Throwable t) {
                    progressHide();
                notif("error, silahkan cek data akun anda!");
            }
        });
    }

    private void trackTopUp() {
        MobilePulsaApiHelper.trackUserTopUp(String.valueOf(dataList.get(spinner.getSelectedItemPosition()).getPhoneCreditPrice()), "PLN", etIdPln.getText().toString(),
                TopUpPlnActivity.this, sp, new ApiListener() {
                    @Override
                    public void onSuccess() {
                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "Permintaan pengisian telah berhasil, kami akan mengirimkan pemberitahuan setelah kami mengkonfirmasi";
                        final User user = BaseApp.getInstance(TopUpPlnActivity.this).getLoginUser();
                        sendNotif(user.getToken_merchant(), notif);
                        progressHide();
                        finish();
                    }

                    @Override
                    public void onError() {
                        progressHide();
                        notif("error, silahkan cek data akun anda!");
                    }
                });
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private void progressShow() {
        rlProgress.setVisibility(View.VISIBLE);
        disableBack = "true";
    }

    private void progressHide() {
        rlProgress.setVisibility(View.GONE);
        disableBack = "false";
    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(TopUpPlnActivity.this).getLoginUser();
        MerchantService userService = ServiceGenerator.createService(MerchantService.class,
                loginUser.getEmail(), loginUser.getPassword());
        FcmRequestJson param = new FcmRequestJson();
        param.setFcm(1);
        userService.fcmgetkey(param).enqueue(new Callback<FcmKeyResponseJson>() {
            @Override
            public void onResponse(Call<FcmKeyResponseJson> call, Response<FcmKeyResponseJson> response) {
                if(response.isSuccessful()){
                    String res = response.body().getResultcode();
                    if(res.equalsIgnoreCase("00")){
                        keyss = response.body().getKeydata();

                    }
                }
            }

            @Override
            public void onFailure(Call<FcmKeyResponseJson> call, Throwable t) {

            }
        });
    }

    private void sendNotif(final String regIDTujuan, final Notif notif) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(notif);

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(TopUpRequestResponse previousResponse) {
        progressShow();
        MobilePulsaApiHelper.checkPrepaidStatus(previousResponse.getRefId(), sp).enqueue(new Callback<TopUpBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopUpBaseResponse> call, @NonNull Response<TopUpBaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        progressHide();
                        TopUpRequestResponse data = response.body().getData();

                        final Dialog dialog = new Dialog(TopUpPlnActivity.this);
                        dialog.setContentView(R.layout.dialog_payment_details);
                        TextView tvPel = dialog.findViewById(R.id.idPelanggan);
                        TextView tvTotal = dialog.findViewById(R.id.tvTotal);
                        TextView titlePel = dialog.findViewById(R.id.tvIdPel);

                        if (data.getMeteredToken() != null && !data.getMeteredToken().isEmpty()) {
                            tvPel.setText(data.getMeteredToken().split("/")[0]);
                        } else {
                            titlePel.setText("STATUS");
                            tvPel.setText(data.getMessage());
                        }

                        int total = data.getPrice() + 600;
                        tvTotal.setText(Utility.convertCurrency(String.valueOf(total), TopUpPlnActivity.this));
                        dialog.show();
                    } else {
                        progressHide();
                        notif(Objects.requireNonNull(response.errorBody()).toString());
                    }
                } else {
                    progressHide();
                    notif("error, silahkan cek data akun anda!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopUpBaseResponse> call, @NonNull Throwable t) {
                progressHide();
                notif("error, silahkan cek data akun anda!");
            }
        });

    }
}
