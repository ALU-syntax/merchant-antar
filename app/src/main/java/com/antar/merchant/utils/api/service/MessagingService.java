package com.antar.merchant.utils.api.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;

import com.antar.merchant.constants.Constants;
import com.antar.merchant.utils.SettingPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.antar.merchant.R;
import com.antar.merchant.activity.ChatActivity;
import com.antar.merchant.activity.MainActivity;
import com.antar.merchant.activity.OrdervalidasiActivity;
import com.antar.merchant.activity.SplashActivity;
import com.antar.merchant.constants.BaseApp;
import com.antar.merchant.models.User;
import com.antar.merchant.models.fcm.DriverResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.core.app.NotificationCompat;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;



@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    Intent intent;
    public static final String BROADCAST_ACTION = "com.delip.merchant";
    public static final String BROADCAST_ORDER = "order";
    Intent intentOrder;
    SettingPreference sp;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
        sp = new SettingPreference(this);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        User user = BaseApp.getInstance(this).getLoginUser();
        if (!remoteMessage.getData().isEmpty() && user != null) {
            parseAndSendMessage(remoteMessage.getData());
        }
        messageHandler(remoteMessage);
    }

    private void parseAndSendMessage(Map<String, String> mapResponse) {
        if (Objects.requireNonNull(mapResponse.get("type")).equals("1")) {
            DriverResponse response = new DriverResponse();
            response.setIddriver(mapResponse.get("id_driver"));
            response.setIdTransaksi(mapResponse.get("id_transaksi"));
            response.setResponse(mapResponse.get("response"));
            EventBus.getDefault().postSticky(response);
        }

    }


    private void messageHandler(RemoteMessage remoteMessage) {
        User user = BaseApp.getInstance(this).getLoginUser();
        if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("1")) {
            if (user != null) {
                orderHandler(remoteMessage);
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("3")) {
            if (user != null) {
                otherHandler(remoteMessage);
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("4")) {
            otherHandler2(remoteMessage);
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("2")) {
            if (user != null) {
                chat(remoteMessage);
            }
        }
    }

    private void otherHandler(RemoteMessage remoteMessage){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void otherHandler2(RemoteMessage remoteMessage){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void orderHandler(RemoteMessage remoteMessage) {
        Bundle data = new Bundle();
        intentToOrder(data);

        if (Objects.equals(remoteMessage.getData().get("response"), "5")) {
            notificationOrderBuilderCancel(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "2")) {
            playSound1();
            notificationOrderBuilderAccept(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "3")) {
            playSound1();
            notificationOrderBuilderStart(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "4")) {
            playSound1();
            notificationOrderBuilderFinish(remoteMessage);
        }
    }

    private void intentToOrder(Bundle bundle){
        intentOrder.putExtras(bundle);
        sendBroadcast(intentOrder);
    }




    private void notificationOrderBuilderCancel(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("id_pelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Batal");
        mBuilder.setContentText(getString(R.string.notification_cancel));
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderStart(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), OrdervalidasiActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Mulai");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderAccept(RemoteMessage remoteMessage) {
        Intent toOrder = new Intent(getBaseContext(), OrdervalidasiActivity.class);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), OrdervalidasiActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Menerima Order");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        boolean inForeground = isAppOnForeground(getApplicationContext());
        timerplay.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !inForeground) {
            orderintent(remoteMessage, toOrder);
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    @TargetApi(29)
    private void orderintent(RemoteMessage remoteMessage, Intent intent1) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_order");
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("HORE KAMU DAPAT PESANAN BARU");
        bigTextStyle.bigText("Cek Pesanannya");

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setOngoing(true);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("HORE KAMU DAPAT PESANAN BARU");
        mBuilder.setContentText("Cek Pesanannya");
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_order";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Order Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(100, mBuilder.build());
    }

    CountDownTimer timerplay = new CountDownTimer(20000, 1000) {
        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            Constants.duration = millisUntilFinished;
        }


        public void onFinish() {
            sp.updateNotif("OFF");
            cancelIncomingCallNotification();
        }
    }.start();

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void notificationOrderBuilderFinish(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Selesai");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());

    }

    private void chat(RemoteMessage remoteMessage){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("senderid", remoteMessage.getData().get("receiverid"));
        intent1.putExtra("receiverid", remoteMessage.getData().get("senderid"));
        intent1.putExtra("name", remoteMessage.getData().get("name"));
        intent1.putExtra("tokenku", remoteMessage.getData().get("tokendriver"));
        intent1.putExtra("tokendriver", remoteMessage.getData().get("tokenuser"));
        intent1.putExtra("pic", remoteMessage.getData().get("pic"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("name"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(remoteMessage.getData().get("name"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "mitra";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel mitra",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void playSound1(){
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.notification);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(2000);
    }

    public void cancelIncomingCallNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(100);
    }

}
