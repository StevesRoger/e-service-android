package org.jarvis.code.ui.customer;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.BaseView;

import retrofit2.Response;

/**
 * Created by ki.kao on 10/10/2017.
 */
@PerActivity
public interface RegisterPresenter<V extends BaseView> extends BasePresenter<V> {

    void submitCustomer() throws Exception;

    void onSubmitCustomerSucceed(Response<ResponseEntity<String>> response);

    void onSubmitCustomerFailed(String message);
}
