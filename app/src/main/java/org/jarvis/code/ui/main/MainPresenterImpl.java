package org.jarvis.code.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.Advertisement;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;
import org.jarvis.code.util.Constants;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter<MainView> {


    private PromotionInteractorImpl promotionInteractor;

    @Inject
    public MainPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
        this.interactor = new AdvertisementInteractorImpl(this);
        this.promotionInteractor = new PromotionInteractorImpl(this);
    }

    @Override
    public void fetchAdvertisement() {
        requestClient.fetchAdvertisement().enqueue(interactor);
    }

    @Override
    public void fetchPromotion() {
        requestClient.fetchPromotions().enqueue(promotionInteractor);
    }

    @Override
    public void onAnimateAD(List<Advertisement> ads) {
        if (view != null && ads != null && !ads.isEmpty()) {
            for (Advertisement ad : ads) {
                if (!Constants.advertisement.containsKey(ad.getId()))
                    Constants.advertisement.put(ad.getId(), ad.getImage());
            }
            view.startAnimateAD();
        }
    }

    @Override
    public Fragment getCurrentFragment(int index) {
        FragmentManager fragmentManager = activity().getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && !fragments.isEmpty())
            return fragments.get(index);
        else return null;
    }
}
