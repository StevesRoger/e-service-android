package org.jarvis.code.ui.register;

import android.view.View;

import org.jarvis.code.ui.base.BaseView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ki.kao on 10/10/2017.
 */

public interface RegisterView extends BaseView, View.OnFocusChangeListener {

    void backToMainActivity();

    RequestBody createCustomerJson();

    MultipartBody.Part[] requestFiles();

    void validate() throws Exception;

    void setRequiredField();

    void browseImage();

    void showProgressDialog();

    void dismissProgressDialog();
}
