package com.antar.merchant.utils.api.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.antar.merchant.activity.OrdervalidasiActivity;
import com.antar.merchant.utils.Log;

import androidx.annotation.Nullable;


public class NewOrderService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Log.e("ordercommand","");
            Intent toOrder = new Intent(this, OrdervalidasiActivity.class);
            toOrder.putExtra("invoice", intent.getStringExtra("invoice"));
            toOrder.putExtra("icon", intent.getStringExtra("icon"));
            toOrder.putExtra("ordertime", intent.getStringExtra("ordertime"));
            toOrder.putExtra("id", intent.getStringExtra("id"));
            toOrder.putExtra("iddriver", intent.getStringExtra("iddriver"));
            toOrder.putExtra("idpelanggan", intent.getStringExtra("idpelanggan"));
            toOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}