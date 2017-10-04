package org.jarvis.code.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.jarvis.code.network.RequestClient;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface IPresenter<V extends IView> {

    void onAttach(V view);

    void onDetach();

    Context context();

    V getView();
}
