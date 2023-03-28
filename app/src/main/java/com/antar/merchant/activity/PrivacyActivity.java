package com.antar.merchant.activity;

import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.antar.merchant.R;
import com.antar.merchant.json.PrivacyRequestJson;
import com.antar.merchant.json.PrivacyResponseJson;
import com.antar.merchant.models.SettingsModel;
import com.antar.merchant.utils.LocaleHelper;
import com.antar.merchant.utils.NetworkUtils;
import com.antar.merchant.utils.api.ServiceGenerator;
import com.antar.merchant.utils.api.service.MerchantService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    WebView webView;
    ImageView backbtn;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        webView = findViewById(R.id.webView);
        backbtn = findViewById(R.id.back_btn);
        webView.setBackgroundColor(Color.TRANSPARENT);
        if (NetworkUtils.isConnected(PrivacyActivity.this)) {
            get();
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void get() {
        PrivacyRequestJson request = new PrivacyRequestJson();

        MerchantService service = ServiceGenerator.createService(MerchantService.class, "admin", "12345");
        service.privacy(request).enqueue(new Callback<PrivacyResponseJson>() {
            @Override
            public void onResponse(Call<PrivacyResponseJson> call, Response<PrivacyResponseJson> response) {

                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        SettingsModel model = response.body().getData().get(0);
                        setResult(model);
                    }
                }
            }

            @Override
            public void onFailure(Call<PrivacyResponseJson> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setResult(SettingsModel getprivacy) {

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = getprivacy.getPrivacy();
        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        webView.loadDataWithBaseURL(null, text, mimeType, encoding, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}

