package org.jarvis.code.ui.main;

import android.support.v4.app.Fragment;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.Advertisement;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.BaseView;

import java.util.List;

/**
 * Created by ki.kao on 10/4/2017.
 */
@PerActivity
public interface MainPresenter<V extends BaseView> extends BasePresenter<V> {

    void fetchAdvertisement();

    void fetchPromotion();

    void onAnimateAD(List<Advertisement> ads);

    Fragment getCurrentFragment(int index);
}
