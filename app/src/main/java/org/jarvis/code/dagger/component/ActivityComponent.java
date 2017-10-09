package org.jarvis.code.dagger.component;

import org.jarvis.code.ui.product.ProductFragment;
import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.dagger.module.ActivityModule;
import org.jarvis.code.dagger.module.PresenterModule;
import org.jarvis.code.ui.main.MainActivity;
import org.jarvis.code.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by KimChheng on 10/2/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PresenterModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(ProductFragment fragment);

    void inject(SplashActivity activity);
}
