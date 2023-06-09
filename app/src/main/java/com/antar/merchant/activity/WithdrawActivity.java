package com.antar.merchant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antar.merchant.R;
import com.antar.merchant.constants.BaseApp;
import com.antar.merchant.item.BankItem;
import com.antar.merchant.json.BankResponseJson;
import com.antar.merchant.json.FcmKeyResponseJson;
import com.antar.merchant.json.FcmRequestJson;
import com.antar.merchant.json.TopUpTrackingResponseJson;
import com.antar.merchant.json.WithdrawRequestJson;
import com.antar.merchant.json.fcm.FCMMessage;
import com.antar.merchant.models.Notif;
import com.antar.merchant.models.User;
import com.antar.merchant.utils.LocaleHelper;
import com.antar.merchant.utils.SettingPreference;
import com.antar.merchant.utils.Utility;
import com.antar.merchant.utils.api.FCMHelper;
import com.antar.merchant.utils.api.ServiceGenerator;
import com.antar.merchant.utils.api.service.MerchantService;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawActivity extends AppCompatActivity {

    EditText amount, bank, accnumber, nama;
    Button submit;
    TextView notif;
    ImageView backbtn, images;
    RelativeLayout rlnotif, rlprogress;
    String disableback, type, nominal;
    SettingPreference sp;
    RecyclerView petunjuk;
    LinearLayout llpentunjuk;
    BankItem bankItem;
    private String keyss;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        getkey();
        disableback = "false";
        amount = findViewById(R.id.amount);
        bank = findViewById(R.id.bank);
        accnumber = findViewById(R.id.accnumber);
        backbtn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        notif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        nama = findViewById(R.id.namanumber);
        images = findViewById(R.id.imagebackground);
        sp = new SettingPreference(this);
        llpentunjuk = findViewById(R.id.llpentunjuk);
        petunjuk = findViewById(R.id.petunjuk);
        sp = new SettingPreference(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (Objects.equals(type, "topup")) {
            images.setImageResource(R.drawable.atm);
            images.setScaleType(ImageView.ScaleType.FIT_XY);
            nominal = intent.getStringExtra("nominal");

//            Utility.currencyTXT(amount, Objects.requireNonNull(nominal), WithdrawActivity.this);
            Utility.convertLocaleCurrencyTV(amount, WithdrawActivity.this, Objects.requireNonNull(nominal));
            petunjuk.setHasFixedSize(true);
            petunjuk.setNestedScrollingEnabled(false);
            petunjuk.setLayoutManager(new GridLayoutManager(this, 1));
            getpetunjuk();
        }

        amount.addTextChangedListener(Utility.currencyTW(amount, this));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userLogin = BaseApp.getInstance(WithdrawActivity.this).getLoginUser();
                if (type.equals("withdraw")) {
                    if (amount.getText().toString().isEmpty()) {
                        notif("nominal tidak boleh kosong!");
                    } else if (Long.parseLong(amount.getText()
                            .toString()
                            .replace(".", "")
                            .replace(",", "")
                            .replace(sp.getSetting()[0], "")) > userLogin.getWalletSaldo()) {
                        notif("saldo anda tidak mencukupi!");
                    } else if (bank.getText().toString().isEmpty()) {
                        notif("bank tidak boleh kosong!");
                    } else if (accnumber.getText().toString().isEmpty()) {
                        notif("nomor rekening tidak boleh kosong!");
                    } else {
                        submit();
                    }
                } else {
                    if (amount.getText().toString().isEmpty()) {
                        notif("nominal tidak boleh kosong!");
                    } else if (bank.getText().toString().isEmpty()) {
                        notif("bank tidak boleh kosong!");
                    } else if (accnumber.getText().toString().isEmpty()) {
                        notif("nomor rekening tidak boleh kosong!");
                    } else {
                        submit();
                    }
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submit() {
        progressshow();
        final User user = BaseApp.getInstance(this).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(bank.getText().toString());
        request.setName(nama.getText().toString());
        request.setAmount(amount.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""));
        request.setCard(accnumber.getText().toString());
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType(type);

        MerchantService service = ServiceGenerator.createService(MerchantService.class, user.getNoTelepon(), user.getPassword());
        service.trackTopUp(request).enqueue(new Callback<TopUpTrackingResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<TopUpTrackingResponseJson> call, @NonNull Response<TopUpTrackingResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(WithdrawActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Notif notif = new Notif();
                        if (type.equals("withdraw")) {
                            notif.title = "Penarikan";
                            notif.message = "Permintaan penarikan telah berhasil, kami akan mengirimkan pemberitahuan setelah kami mengirim dana ke akun Anda";
                        } else {
                            notif.title = "Topup";
                            notif.message = "Permintaan pengisian telah berhasil, kami akan mengirimkan pemberitahuan setelah kami menambah saldo anda";
                        }
                        sendNotif(user.getToken_merchant(), notif);

                    } else {
                        notif("error, silahkan periksa data akun anda!");
                    }
                } else {
                    notif("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopUpTrackingResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("");
            }
        });
    }

    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 4000);
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(WithdrawActivity.this).getLoginUser();
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
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getpetunjuk() {
        User user = BaseApp.getInstance(this).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();

        MerchantService service = ServiceGenerator.createService(MerchantService.class, user.getNoTelepon(), user.getPassword());
        service.listbank(request).enqueue(new Callback<BankResponseJson>() {
            @Override
            public void onResponse(Call<BankResponseJson> call, Response<BankResponseJson> response) {
                if (response.isSuccessful()) {
                    llpentunjuk.setVisibility(View.VISIBLE);
                    bankItem = new BankItem(WithdrawActivity.this, Objects.requireNonNull(response.body()).getData(), R.layout.item_bank);
                    petunjuk.setAdapter(bankItem);
                }
            }

            @Override
            public void onFailure(Call<BankResponseJson> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
