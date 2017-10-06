package org.jarvis.code.dagger.module;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.TabAdapter;
import org.jarvis.code.dagger.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KimChheng on 10/2/2017.
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }
}
