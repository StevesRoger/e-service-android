package org.jarvis.code.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.jarvis.code.util.Constant;
import org.jarvis.code.util.RequestFactory;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by KimChheng on 9/3/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }

    private void registerToken(String token) {
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "/mobile/register")
                .build();
        try {
            RequestFactory.okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
