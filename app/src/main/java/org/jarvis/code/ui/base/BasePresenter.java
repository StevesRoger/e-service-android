package org.jarvis.code.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.network.RequestClient;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected final Context context;
    protected final RequestClient requestClient;

    private V view;

    @Inject
    public BasePresenter(@ActivityContext Context context, RequestClient requestClient) {
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

}
