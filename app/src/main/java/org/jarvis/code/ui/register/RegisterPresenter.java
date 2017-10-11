package org.jarvis.code.ui.register;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.BaseView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */
@PerActivity
public interface RegisterPresenter<V extends BaseView> extends BasePresenter<V> {

    void submitCustomer() throws Exception;

    void onSubmitCustomerSucceed(Response<ResponseEntity<Map<String, Object>>> response);

    void onSubmitCustomerFailed(String message);
}
