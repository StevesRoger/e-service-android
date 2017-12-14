package org.jarvis.code.util;

import android.Manifest;
import android.support.v4.util.ArrayMap;

/**
 * Created by KimChheng on 5/28/2017.
 */

public final class Constants {

    public static final String BASE_URL = "https://e-service-application.herokuapp.com/api/";
    //public static final String BASE_URL = "http://10.0.3.2:8080/api/";
    public static final String TAG = "=====>>> V-Printing:";
    public static final String FCM_BROADCAST_PRODUCT_WED = "org.jarvis.code.broadcast_product_wed";
    public static final String FCM_BROADCAST_PRODUCT_DES = "org.jarvis.code.broadcast_product_des";
    public static final String FCM_BROADCAST_PRODUCT_CER = "org.jarvis.code.broadcast_product_cer";
    public static final String FCM_BROADCAST_PROMOTION = "org.jarvis.code.broadcast_promotion";
    public static final String FCM_BROADCAST_ADVERTISEMENT = "org.jarvis.code.broadcast_advertisement";
    public static final int REQUEST_PERMISSIONS_CODE = 200;
    public static final String[] MY_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public static final ArrayMap<Integer, Integer> advertisement = new ArrayMap<>();
}
