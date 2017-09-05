package org.jarvis.code.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ki.kao on 9/5/2017.
 */

public final class BasicAuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request original = chain.request();
        final Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", "Basic YWRtaW46MTE=")
                .method(original.method(), original.body());
        return chain.proceed(requestBuilder.build());
    }
}
