package org.jarvis.code.ui.register;

import org.jarvis.code.model.read.ResponseEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */

public class RegisterInteractorImpl implements RegisterInteractor<Map<String, Object>> {

    private RegisterPresenter<RegisterView> presenter;

    public RegisterInteractorImpl(RegisterPresenter<RegisterView> presenter) {
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
}
