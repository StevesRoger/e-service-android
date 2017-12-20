package org.jarvis.code;

import android.app.Application;
import android.content.Context;

import org.jarvis.code.dagger.component.ApplicationComponent;
import org.jarvis.code.dagger.component.DaggerApplicationComponent;
import org.jarvis.code.dagger.module.ApplicationModule;
import org.jarvis.code.util.Constants;

/**
 * Created by KimChheng on 10/1/2017.
 */

public class VPrintApplication extends Application {

    private ApplicationComponent applicationComponent;


    public static VPrintApplication get(Context context) {
        return (VPrintApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Constants.advertisement.clear();
        Constants.promotion.clear();
    }
}
