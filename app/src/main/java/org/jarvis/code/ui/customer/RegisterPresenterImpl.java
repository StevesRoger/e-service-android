package org.jarvis.code.ui.customer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.R;
import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */

public class RegisterPresenterImpl extends BasePresenterImpl<RegisterView> implements RegisterPresenter<RegisterView> {

    @Inject
    public RegisterPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
        this.interactor = new RegisterInteractorImpl(this);
    }

    @Override
    public void submitCustomer(int which) throws Exception {
        view.validate();
        RequestBody requestJson = view.createCustomerJson();
        MultipartBody.Part[] requestFiles = view.requestFiles();
        view.showProgressDialog();
        if (which == Constants.CUSTOMER)
            requestClient.submitCustomer(requestJson, requestFiles).enqueue(interactor);
        else if (which == Constants.ENTITIES)
            requestClient.submitEntities(requestJson, requestFiles).enqueue(interactor);
    }

    @Override
    public void onSubmitCustomerSucceed(Response<ResponseEntity<String>> response) {
        if (view != null) {
            view.dismissProgressDialog();
            view.showSweetAlert(SweetAlertDialog.SUCCESS_TYPE, activity().getString(R.string.send_information), activity().getString(R.string.successed));
            ResponseEntity<String> responseEntity = response.body();
            Loggy.i(RegisterFragment.class, responseEntity.getMessage());
        }
    }

    @Override
    public void onSubmitCustomerFailed(String message) {
        if (view != null) {
            view.dismissProgressDialog();
            view.showSweetAlert(SweetAlertDialog.ERROR_TYPE, activity().getString(R.string.fail), message);
            Loggy.e(RegisterPresenterImpl.class, message);
        }
    }
}
