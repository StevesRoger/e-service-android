package org.jarvis.code.dagger.module;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.ui.main.MainPresenter;
import org.jarvis.code.ui.main.MainPresenterImpl;
import org.jarvis.code.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ki.kao on 10/4/2017.
 */
@Module
public class PresenterModule {

    @Provides
    @PerActivity
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl<MainView> mainPresenter) {
        return mainPresenter;
    }
}
