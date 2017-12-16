package org.jarvis.code.network;

import android.content.Context;

import org.jarvis.code.util.Loggy;

import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by ki.kao on 12/16/2017.
 */

public class HttpRequest {

    private OkHttpClient client;
    private Request request;
    private Context context;
    private String url;

    public HttpRequest() {
        client = new OkHttpClient();
    }

    public HttpRequest(Context context) {
        this();
        this.context = context;
    }

    public HttpRequest(Context context, String url) {
        this(context);
        this.context = context;
        this.url = url;
    }

    public void doPost(FormData formData) {
        request = new Request.Builder()
                .url(url)
                .post(formData.getRequestBody())
                .build();
    }

    public void doGet() {
        request = new Request.Builder()
                .url(url)
                .build();
    }

    public HttpRequest buildQueryParamUrl(Map<String, String> values) {
        if (url == null || url.isEmpty()) try {
            throw new Exception("url can not empty..!!");
        } catch (Exception e) {
            e.printStackTrace();
            Loggy.e(HttpRequest.class, e.getMessage());
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> entry : values.entrySet())
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        url = urlBuilder.build().toString();
        return this;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest context(Context context) {
        this.context = context;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }
}
