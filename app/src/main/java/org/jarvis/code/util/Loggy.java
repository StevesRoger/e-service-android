package org.jarvis.code.util;

import android.util.Log;

/**
 * Created by ki.kao on 9/1/2017.
 */

public final class Loggy {

    public static void e(Class clazz, String message) {
        Log.e(Constants.TAG + clazz.getSimpleName(), message);
    }

    public static void d(Class clazz, String message) {
        Log.d(Constants.TAG + clazz.getSimpleName(), message);
    }

    public static void i(Class clazz, String message) {
        Log.i(Constants.TAG + clazz.getSimpleName(), message);
    }

    public static void v(Class clazz, String message) {
        Log.v(Constants.TAG + clazz.getSimpleName(), message);
    }
}
