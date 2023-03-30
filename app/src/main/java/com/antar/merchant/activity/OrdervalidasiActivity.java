package com.antar.merchant.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antar.merchant.models.ItemPesananModel;
import com.antar.merchant.utils.LocaleHelper;
import com.antar.merchant.utils.PicassoTrustAll;
import com.antar.merchant.utils.PrintUtils;
import com.antar.merchant.utils.PrinterCommands;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.antar.merchant.R;
import com.antar.merchant.constants.BaseApp;
import com.antar.merchant.constants.Constants;
import com.antar.merchant.item.MenuItem;
import com.antar.merchant.json.DetailRequestJson;
import com.antar.merchant.json.DetailTransResponseJson;
import com.antar.merchant.models.DriverModel;
import com.antar.merchant.models.PelangganModel;
import com.antar.merchant.models.TransaksiModel;
import com.antar.merchant.models.User;
import com.antar.merchant.models.fcm.DriverResponse;
import com.antar.merchant.utils.Utility;
import com.antar.merchant.utils.api.ServiceGenerator;
import com.antar.merchant.utils.api.service.MerchantService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class OrdervalidasiActivity extends AppCompatActivity {

    ImageView backbtn, telpdriver, chatdriver, telpbuyer, chatbuyer;
    LinearLayout dialogedit, lldriver, llchatdriver, llpelanggan, llchatpelanggan, llprice, llverify;
    TextView nomorinvoice, waktu, namadriver, iddriver, namabuyer, alamatbuyer, metodepembayaran, totalharga, kodevalidasi,status;
    ShimmerFrameLayout shimmeritem, shimmerdriver, shimmerpelanggan, shimmerprice;
    String invoice, id_transaksi, idpelanggan, id_driver, time;
    RecyclerView itemmenu;
    MenuItem menuitem;
    AppCompatButton btnPrintNota;
    private TextView dibayardriver;
    private static final int REQUEST_PERMISSION_CALL = 992;

    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    private String merchantAddress;
    private String merchantPhone;
    private String merchantName;
    private int merchantImage;
    private String buyerName;
    private String driverName;
    private String transactionDestination;
    private String transactionId;
    private String transactionTotal;
    List<ItemPesananModel> modelItemPesanan;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordervalidasi);
        backbtn = findViewById(R.id.back_btn);
        dibayardriver = findViewById(R.id.dibayardriver);
        dialogedit = findViewById(R.id.detailorder);
        telpdriver = findViewById(R.id.telpdriver);
        chatdriver = findViewById(R.id.chatdriver);
        telpbuyer = findViewById(R.id.telpbuyer);
        chatbuyer = findViewById(R.id.chatbuyer);
        nomorinvoice = findViewById(R.id.nomorinvoice);
        waktu = findViewById(R.id.waktu);
        namadriver = findViewById(R.id.namadriver);
        iddriver = findViewById(R.id.iddriver);
        namabuyer = findViewById(R.id.namabuyer);
        alamatbuyer = findViewById(R.id.alamatbuyer);
        metodepembayaran = findViewById(R.id.metodepembayaran);
        totalharga = findViewById(R.id.totalharga);
        kodevalidasi = findViewById(R.id.kodevalidasi);
        shimmeritem = findViewById(R.id.shimmeritem);
        lldriver = findViewById(R.id.lldriver);
        llpelanggan = findViewById(R.id.llpelanggan);
        llchatdriver = findViewById(R.id.llchatdriver);
        llchatpelanggan = findViewById(R.id.llchatpelanggan);
        llprice = findViewById(R.id.llprice);
        llverify = findViewById(R.id.codeverify);
        shimmerdriver = findViewById(R.id.shimmerdriver);
        shimmerpelanggan = findViewById(R.id.shimmerpelanggan);
        shimmerprice = findViewById(R.id.shimmerprice);
        itemmenu = findViewById(R.id.itemmenu);
        status = findViewById(R.id.status);
        btnPrintNota = findViewById(R.id.btn_print_nota);

        Intent intent = getIntent();
        invoice = intent.getStringExtra("invoice");
        time = intent.getStringExtra("ordertime");
        id_transaksi = intent.getStringExtra("id");
        id_driver = intent.getStringExtra("iddriver");
        idpelanggan = intent.getStringExtra("idpelanggan");

        nomorinvoice.setText(invoice);
        waktu.setText(time);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPrintNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBill();
            }
        });

        itemmenu.setHasFixedSize(true);
        itemmenu.setNestedScrollingEnabled(false);
        itemmenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        getdata();
    }

    private void shimmershow() {
        shimmeritem.startShimmerAnimation();
        shimmerdriver.startShimmerAnimation();
        shimmerpelanggan.startShimmerAnimation();
        shimmerprice.startShimmerAnimation();
        lldriver.setVisibility(View.GONE);
        llpelanggan.setVisibility(View.GONE);
        llprice.setVisibility(View.GONE);
        llverify.setVisibility(View.GONE);

    }

    private void shimmertutup() {
        lldriver.setVisibility(View.VISIBLE);
        llpelanggan.setVisibility(View.VISIBLE);
        llprice.setVisibility(View.VISIBLE);
        llverify.setVisibility(View.VISIBLE);

        shimmeritem.stopShimmerAnimation();
        shimmerdriver.stopShimmerAnimation();
        shimmerpelanggan.stopShimmerAnimation();
        shimmerprice.stopShimmerAnimation();

        itemmenu.setVisibility(View.VISIBLE);

        shimmeritem.setVisibility(View.GONE);
        shimmerdriver.setVisibility(View.GONE);
        shimmerpelanggan.setVisibility(View.GONE);
        shimmerprice.setVisibility(View.GONE);
    }

    private void getdata() {
        shimmershow();
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        DetailRequestJson request = new DetailRequestJson();
        request.setNotelepon(loginUser.getNoTelepon());
        request.setId(id_transaksi);
        request.setIdDriver(id_driver);
        request.setIdpelanggan(idpelanggan);

        merchantName = loginUser.getNamamerchant();
        merchantAddress = loginUser.getAlamat_merchant();
        merchantPhone = loginUser.getPhone();


        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.detailtrans(request).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DetailTransResponseJson> call, Response<DetailTransResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();
                        final DriverModel driver = response.body().getDriver().get(0);
                        final PelangganModel pelanggan = response.body().getPelanggan().get(0);
                        TransaksiModel transaksi = response.body().getData().get(0);

                        driverName = driver.getNamaDriver();
                        buyerName = pelanggan.getFullnama();
                        transactionDestination = transaksi.getAlamatTujuan();
                        getMerchantImage(Constants.IMAGESDRIVER+driver.getFoto());
                        transactionId = transaksi.getStruk();
                        transactionTotal = transaksi.getTotal_biaya();

                        namadriver.setText(driver.getNamaDriver());
                        iddriver.setText(driver.getMerek()+" "+getString(R.string.text_with_bullet)+" "+driver.getNomor_kendaraan());
                        namabuyer.setText(pelanggan.getFullnama());
                        alamatbuyer.setText(transaksi.getAlamatTujuan());

                        if (transaksi.status == 4 || transaksi.status == 5) {
                            llchatdriver.setVisibility(View.GONE);
                            llchatpelanggan.setVisibility(View.GONE);
                        } else {
                            llchatdriver.setVisibility(View.VISIBLE);
                            llchatpelanggan.setVisibility(View.VISIBLE);
                        }

                        if (transaksi.isPakaiWallet()) {
                            metodepembayaran.setText("Total Price (Wallet)");
                        } else {
                            metodepembayaran.setText("Total Price (Cash)");
                        }

                        status.setVisibility(View.VISIBLE);

                        if (transaksi.status == 2) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.colorgradient));
                            status.setText("New Order");
                        } else if (transaksi.status ==3) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.yellow));
                            status.setText("Delivery");
                        } else if (transaksi.status ==4) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.green));
                            status.setText("Finish");
                        } else if (transaksi.status ==5) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.red));
                            status.setText("Cancel");
                        }

                        chatdriver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkReadStoragepermission()) {
                                    Intent intent = new Intent(OrdervalidasiActivity.this, ChatActivity.class);
                                    intent.putExtra("senderid", loginUser.getId_merchant());
                                    intent.putExtra("receiverid", driver.getId());
                                    intent.putExtra("name", driver.getNamaDriver());
                                    intent.putExtra("tokendriver", loginUser.getToken_merchant());
                                    intent.putExtra("tokenku", driver.getRegId());
                                    intent.putExtra("pic", Constants.IMAGESDRIVER+driver.getFoto());
                                    startActivity(intent);
                                }

                            }
                        });

                        chatbuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkReadStoragepermission()) {
                                    Intent intent = new Intent(OrdervalidasiActivity.this, ChatActivity.class);
                                    intent.putExtra("senderid", loginUser.getId_merchant());
                                    intent.putExtra("receiverid", pelanggan.getId());
                                    intent.putExtra("name", pelanggan.getFullnama());
                                    intent.putExtra("tokendriver", loginUser.getToken_merchant());
                                    intent.putExtra("tokenku", pelanggan.getToken());
                                    intent.putExtra("pic", Constants.IMAGESPELANGGAN+pelanggan.getFoto());
                                    startActivity(intent);
                                }

                            }
                        });

                        telpdriver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrdervalidasiActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + driver.getNamaDriver() + "(" + driver.getNoTelepon() + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(OrdervalidasiActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(OrdervalidasiActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + driver.getNoTelepon()));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        telpbuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrdervalidasiActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Customer");
                                alertDialogBuilder.setMessage("You want to call " + pelanggan.getFullnama() + "(" + pelanggan.getNoTelepon() + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(OrdervalidasiActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(OrdervalidasiActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + pelanggan.getNoTelepon()));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        Double dibayar = Double.parseDouble(transaksi.getTotal_biaya())/1.1;
                        String dibayardr = String.valueOf(dibayar);
//                        Utility.currencyTXT(totalharga,transaksi.getTotal_biaya(),OrdervalidasiActivity.this);
//                        Utility.currencyTXT(dibayardriver, dibayardr,OrdervalidasiActivity.this);
                        Utility.convertLocaleCurrencyTV(totalharga, OrdervalidasiActivity.this, transaksi.getTotal_biaya());
                        Utility.convertLocaleCurrencyTV(dibayardriver, OrdervalidasiActivity.this, dibayardr);
                        kodevalidasi.setText(transaksi.getStruk());

                        orderItem(response.body().getItem());

                        menuitem = new MenuItem(OrdervalidasiActivity.this, response.body().getItem(), R.layout.item_menu);
                        itemmenu.setAdapter(menuitem);


                    }
                }
            }

            @Override
            public void onFailure(Call<DetailTransResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(OrdervalidasiActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse());
        if (response.getIdTransaksi().equals(id_transaksi)) {
            switch (response.getResponse()) {
                case "2":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.colorgradient));
                    status.setText("New Order");
                    break;
                case "3":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.yellow));
                    status.setText("Delivery");
                    break;
                case "4":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.green));
                    status.setText("Finish");
                    break;
                case "5":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.red));
                    status.setText("Cancel");
                    break;
            }
        }

    }

    private void orderItem(List<ItemPesananModel> modelList){
        modelItemPesanan = modelList;
    }

    protected void printBill() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
            this.startActivityForResult(BTIntent, DeviceListActivity.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B,0x21,0x03};
                outputStream.write(printformat);


                printCustom("Merchant Antar",2,1);
//                printCustom("Merchant Antar Partner",0,1);
                printPhoto(merchantImage);
                printCustom("Alamat: " + merchantAddress,0,1);
                printCustom("Nomor Telfon: " + merchantPhone,0,1);
                String dateTime[] = getDateTime();
                printText(leftRightAlign(dateTime[0], dateTime[1]));
                printNewLine();
                printCustom(transactionId,0,1);
                printCustom(buyerName,0,1);
                printCustom(driverName,0,1);
                printNewLine();
                printCustom(transactionDestination,0,1);
                printNewLine();
                for(ItemPesananModel itemPesananModel : modelItemPesanan){
                    printCustom(itemPesananModel.getNama_item(),0,1);
                    printCustom(itemPesananModel.getJumlah_item(),0,1);
                    printCustom(itemPesananModel.getCatatan(),0,1);
                    printCustom(itemPesananModel.getTotalharga(),0,1);
                    printNewLine();
                }

                printText(leftRightAlign("Total: " , transactionTotal));
                printNewLine();
                printCustom("Thank you for coming & we look",0,1);
                printCustom("forward to serve you again",0,1);
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = PrintUtils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(PrintUtils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    public void getMerchantImage(String uri){
        PicassoTrustAll.getInstance(this).load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                merchantImage = bitmap.hashCode();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
    }

}
