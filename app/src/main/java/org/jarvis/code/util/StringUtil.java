package org.jarvis.code.util;

import android.content.Context;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by ki.kao on 12/7/2017.
 */

public final class StringUtil {

    public static HashMap<String, String> getStringAsMap(Context context, int id) {
        HashMap<String, String> myMap = new Gson().fromJson(context.getString(id), HashMap.class);
        return myMap;
    }
}
