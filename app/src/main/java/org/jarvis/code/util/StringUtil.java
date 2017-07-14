package org.jarvis.code.util;

import android.content.Context;

/**
 * Created by KimChheng on 6/18/2017.
 */

public final class StringUtil {

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }
}
