package org.jarvis.code.util;

/**
 * Created by KimChheng on 5/28/2017.
 */

public final class RequestFactory {

    private RequestFactory() {
    }

    public static <T> T build(Class<T> clazz) {
        return RetrofitUtil.RETROFIT.create(clazz);
    }
}
