package org.jarvis.code.ui.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface BasePresenter<V extends BaseView> {

    void onAttach(V view);

    void onDetach();

    Context context();

    AppCompatActivity activity();

    V getView();

    void showMessage(String message);

    void showSnackBar(String message);

    BaseInteractor getInteractor();
}
