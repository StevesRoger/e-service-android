package org.jarvis.code.ui.base;

import android.content.Context;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.network.RequestClient;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    protected final Context context;
    protected final RequestClient requestClient;

    protected V view;

    @Inject
    public BasePresenterImpl(@ActivityContext Context context, RequestClient requestClient) {
        this.context = context;
        this.requestClient = requestClient;
    }

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public Context context() {
        return context;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void showMessage(String message) {
        if (view != null)
            view.showMessage(message);

    }

    @Override
    public void showSnackBar(String message) {
        if (view != null)
            view.showSnackBar(message);
    }

}
