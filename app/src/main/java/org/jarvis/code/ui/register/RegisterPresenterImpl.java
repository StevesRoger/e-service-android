package org.jarvis.code.ui.register;

import android.content.Context;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.write.Customer;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/10/2017.
 */

public class RegisterPresenterImpl extends BasePresenterImpl<RegisterView> implements RegisterPresenter<RegisterView> {

    @Inject
    public RegisterPresenterImpl(@ActivityContext Context context, RequestClient requestClient) {
        super(context, requestClient);
    }

    @Override
    public void submitCustomer() {

    }
}
