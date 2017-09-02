package org.jarvis.code.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by KimChheng on 5/28/2017.
 */

public final class RetrofitUtil {

    public final static Retrofit RETROFIT;

    static {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        RETROFIT = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(JacksonConverterFactory.create())
                // .client(okHttpClient)
                .build();
    }
}
