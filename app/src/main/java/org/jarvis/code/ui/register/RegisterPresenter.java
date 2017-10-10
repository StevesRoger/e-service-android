package org.jarvis.code.ui.register;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.write.Customer;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.BaseView;

/**
 * Created by ki.kao on 10/10/2017.
 */
@PerActivity
public interface RegisterPresenter<V extends BaseView> extends BasePresenter<V> {

    void submitCustomer();
}
