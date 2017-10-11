package org.jarvis.code.dagger.component;

import android.app.Application;
import android.content.Context;

import org.jarvis.code.dagger.ApplicationContext;
import org.jarvis.code.dagger.module.ApplicationModule;
import org.jarvis.code.dagger.module.NetworkModule;
import org.jarvis.code.network.RequestClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by KimChheng on 10/2/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    RequestClient requestClient();
}