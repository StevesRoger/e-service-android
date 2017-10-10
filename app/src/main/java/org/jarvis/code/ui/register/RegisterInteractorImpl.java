package org.jarvis.code.ui.register;

import org.jarvis.code.model.read.ResponseEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */

public class RegisterInteractorImpl implements RegisterInteractor<Map<String, Object>> {

    @Override
    public void onResponse(Call<ResponseEntity<Map<String, Object>>> call, Response<ResponseEntity<Map<String, Object>>> response) {

    }

    @Override
    public void onFailure(Call<ResponseEntity<Map<String, Object>>> call, Throwable t) {

    }
}
