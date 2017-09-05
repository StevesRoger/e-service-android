package org.jarvis.code.util;

import org.jarvis.code.api.BasicAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KimChheng on 5/28/2017.
 */

public final class RetrofitUtil {

    public final static Retrofit RETROFIT;
    public final static OkHttpClient okHttpClient;

    static {

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        RETROFIT = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
