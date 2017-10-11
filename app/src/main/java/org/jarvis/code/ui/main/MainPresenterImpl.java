package org.jarvis.code.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter<MainView> {

    @Inject
    public MainPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
        this.interactor = new AdvertisementInteractorImpl(this);
    }

    @Override
    public void fetchAdvertisement() {
        requestClient.fetchAdvertisement().enqueue(interactor);
    }

    @Override
    public void onAnimateAD(List<Advertisement> ads) {
        if (view != null && ads != null && !ads.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (Advertisement ad : ads)
                list.add(ad.getImage());
            view.startAnimateAD(list);
        }
    }

    @Override
    public Fragment getCurrentFragment(int index) {
        FragmentManager fragmentManager = ((AppCompatActivity) context()).getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && !fragments.isEmpty())
            return fragments.get(index);
        else return null;
    }

}
