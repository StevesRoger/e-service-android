package org.jarvis.code.ui.main;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.ui.base.IPresenter;
import org.jarvis.code.ui.base.IView;

import java.util.List;

/**
 * Created by ki.kao on 10/4/2017.
 */
@PerActivity
public interface MainPresenter<V extends IView> extends IPresenter<V> {

    void fetchAdvertisement();

    void onAnimateAD(List<Advertisement> ads);

    void onDestroy();
}
