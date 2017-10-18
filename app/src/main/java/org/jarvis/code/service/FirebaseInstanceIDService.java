package org.jarvis.code.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.jarvis.code.dagger.VPrintApplication;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by KimChheng on 9/3/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Loggy.i(FirebaseInstanceIDService.class, "onTokenRefresh");
        registerToken(token);
    }

    private void registerToken(String token) {
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constants.BASE_URL + "/mobile/register")
                .build();
        try {
            OkHttpClient okHttpClient = ((VPrintApplication) getApplication()).getComponent().okHttpClient();
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                Loggy.i(FirebaseInstanceIDService.class, "Register token success.");
                Loggy.i(FirebaseInstanceIDService.class, "token: " + token);
            } else
                Loggy.i(FirebaseInstanceIDService.class, "Register token failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
