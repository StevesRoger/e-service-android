package org.jarvis.code.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ListAdapter;
import org.jarvis.code.adapter.TabAdapter;
import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.service.FirebaseBroadcastReceiver;
import org.jarvis.code.ui.product.ProductPresenterImpl;
import org.jarvis.code.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KimChheng on 10/2/2017.
 */
@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    ListAdapter provideListAdapter() {
        return new ListAdapter(activity);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerActivity
    TabAdapter provideTabAdapter(FragmentManager fragmentManager, @Named("titles") List<String> titles) {
        return new TabAdapter(fragmentManager, titles);
    }

    @Provides
    Validator provideValidator() {
        return new Validator(activity);
    }

    @Provides
    @Named("promotion")
    @PerActivity
    FirebaseBroadcastReceiver provideBroadcastReceiverPromotion(ProductPresenterImpl productPresenter) {
        return new FirebaseBroadcastReceiver(productPresenter.getPromotionInteractor());
    }

    @Provides
    @Named("titles")
    List<String> provideTabTitle() {
        List<String> titles = new ArrayList<>();
        titles.add(activity.getString(R.string.general_fragment));
        titles.add(activity.getString(R.string.wedding_fragment));
        titles.add(activity.getString(R.string.design_fragment));
        return titles;
    }
}
