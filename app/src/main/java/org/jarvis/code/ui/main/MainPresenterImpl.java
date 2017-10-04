package org.jarvis.code.ui.main;

import android.content.Context;

import org.jarvis.code.dagger.ApplicationContext;
import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.IView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class MainPresenterImpl<V extends IView> extends BasePresenter<V> implements MainPresenter<V> {

    private MainView mainView;
    private AdvertisementInteractor interactor;

    @Inject
    public MainPresenterImpl(@ApplicationContext Context context, RequestClient requestClient) {
        super(context, requestClient);
        this.interactor = new AdvertisementInteractorImpl(this);
        this.mainView = (MainView) getView();
    }

    @Override
    public void fetchAdvertisement() {
        requestClient.fetchAdvertisement().enqueue(interactor);
    }

    @Override
    public void onAnimateAD(List<Advertisement> ads) {
        if (mainView != null) {
            if (ads != null && !ads.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                for (Advertisement ad : ads)
                    list.add(ad.getImage());
                mainView.startAnimateAD(list);
            }
        }
    }

    @Override
    public void onDestroy() {

    }
}
