package org.jarvis.code.util;

import android.Manifest;

/**
 * Created by KimChheng on 5/28/2017.
 */

public final class Constant {

    //public static final String BASE_URL = "https://e-service-application.herokuapp.com/api/";
    public static final String BASE_URL = "http://10.0.3.2:8080/api/";
    public static final String TAG = "=====>>> E-Service";
    public static final int REQUEST_PERMISSIONS_CODE = 200;
    public static final String[] PERMISSIONS_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


}
