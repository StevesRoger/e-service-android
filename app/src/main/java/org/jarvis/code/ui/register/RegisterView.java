package org.jarvis.code.ui.register;

import android.view.View;

import org.jarvis.code.model.write.Customer;
import org.jarvis.code.ui.base.BaseView;

/**
 * Created by ki.kao on 10/10/2017.
 */

public interface RegisterView extends BaseView, View.OnClickListener, View.OnFocusChangeListener {

    void showDialogDatePicker();

    void backToMainActivity();

    Customer createCustomer();

    void validate() throws Exception;

    void requiredField();

    void browseImage();
}
