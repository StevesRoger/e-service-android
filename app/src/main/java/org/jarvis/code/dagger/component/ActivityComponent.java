package org.jarvis.code.dagger.component;

import android.app.Application;
import android.content.Context;

import org.jarvis.code.activity.MainActivity;
import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.dagger.ApplicationContext;
import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.dagger.module.ActivityModule;
import org.jarvis.code.dagger.module.NetworkModule;

import dagger.Component;

/**
 * Created by KimChheng on 10/2/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

}
