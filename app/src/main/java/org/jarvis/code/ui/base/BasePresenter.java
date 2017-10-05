package org.jarvis.code.ui.base;

import android.content.Context;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface BasePresenter<V extends BaseView> {

    void onAttach(V view);

    void onDetach();

    Context context();

    V getView();

    void showMessage(String message);

    void showSnackBar(String message);
}
