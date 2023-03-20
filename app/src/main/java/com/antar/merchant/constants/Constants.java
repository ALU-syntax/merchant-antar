package com.antar.merchant.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;



public class Constants {

//    private static final String BASE_URL = "https://admin.sigesitpro.com/";
//    private static final String BASE_URL = "https://admin.bumdes-ku.com/";
    private static final String BASE_URL = "https://antar.shop/";

    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESITEM = BASE_URL + "images/itemmerchant/";
    public static final String IMAGESPELANGGAN = BASE_URL + "images/pelanggan/";

    public static long duration;
    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";

    public static String OPERATOR = "OPERATOR_LIST";
    public static String MOBILEPULSA_PRODUCTION_URL = "https://api.mobilepulsa.net/v1/legacy/index";
//    public static String MOBILEPULSA_DEV_URL = "https://testprepaid.mobilepulsa.net/v1/legacy/index";

}
