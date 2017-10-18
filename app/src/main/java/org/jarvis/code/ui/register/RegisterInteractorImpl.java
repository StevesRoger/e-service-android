package org.jarvis.code.ui.register;

import android.content.Context;

import org.jarvis.code.model.ResponseEntity;
import org.json.JSONArray;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */

public class RegisterInteractorImpl implements RegisterInteractor<Map<String, Object>> {

    private RegisterPresenter<RegisterView> presenter;

    public RegisterInteractorImpl(RegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Map<String, Object>>> call, Response<ResponseEntity<Map<String, Object>>> response) {
        if (presenter != null && response.isSuccessful())
            presenter.onSubmitCustomerSucceed(response);
    }

    @Override
    public void onFailure(Call<ResponseEntity<Map<String, Object>>> call, Throwable t) {
        if (presenter != null)
            presenter.onSubmitCustomerFailed(t.getMessage());
    }

    @Override
    public void onNewItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onUpdateItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onDeleteItem(Context context, JSONArray jsonArray) {

    }
}
