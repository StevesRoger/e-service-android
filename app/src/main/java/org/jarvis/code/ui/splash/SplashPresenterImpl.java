package org.jarvis.code.ui.splash;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/9/2017.
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashView> implements SplashPresenter<SplashView> {

    @Inject
    public SplashPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
    }
}
