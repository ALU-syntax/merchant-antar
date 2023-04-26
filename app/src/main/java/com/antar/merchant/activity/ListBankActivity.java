package com.antar.merchant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.antar.merchant.R;
import com.antar.merchant.constants.BaseApp;
import com.antar.merchant.item.ListBankItem;
import com.antar.merchant.json.ListBankActiveResponseJson;
import com.antar.merchant.models.User;
import com.antar.merchant.utils.api.ServiceGenerator;
import com.antar.merchant.utils.api.service.MerchantService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBankActivity extends AppCompatActivity {

    RecyclerView rvListBank;
    ImageView ivBackButton;
    ListBankItem listBankItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bank);

        ivBackButton = findViewById(R.id.iv_back_btn);
        rvListBank = findViewById(R.id.rv_list_bank);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.listBankActive().enqueue(new Callback<ListBankActiveResponseJson>() {
            @Override
            public void onResponse(Call<ListBankActiveResponseJson> call, Response<ListBankActiveResponseJson> response) {
                if(Objects.requireNonNull(response.body()).getBankModel().isEmpty()){
//                  rlnodata.setVisibility(View.VISIBLE);
                }else{
//                    listBankItem = new BankItem(ListBankActivity.this, response.body().getBankModel(), R.layout.item_list_bank);
                    listBankItem = new ListBankItem(response.body().getBankModel(), ListBankActivity.this, R.layout.item_list_bank);

                    rvListBank.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListBankActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvListBank.setLayoutManager(linearLayoutManager);
                    rvListBank.setAdapter(listBankItem);
                }
            }

            @Override
            public void onFailure(Call<ListBankActiveResponseJson> call, Throwable t) {

            }
        });

    }
}