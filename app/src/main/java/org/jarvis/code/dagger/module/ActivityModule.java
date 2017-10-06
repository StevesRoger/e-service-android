package org.jarvis.code.dagger.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.ProductAdapter;
import org.jarvis.code.core.adapter.TabAdapter;
import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.read.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

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
    Activity provideActivity() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    ProductAdapter provideProductAdapter() {
        return new ProductAdapter(activity, new ArrayList<Product>());
    }

    @Provides
    LocalBroadcastManager provideLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(activity);
    }

    @Provides
    TabAdapter provideTabAdapter(FragmentManager fragmentManager, @Named("titles") List<String> titles) {
        return new TabAdapter(fragmentManager, titles);
    }

    @Provides
    @Named("titles")
    List<String> provideTabTitle() {
        List<String> titles = new ArrayList<>();
        titles.add(activity.getString(R.string.wedding_fragment));
        titles.add(activity.getString(R.string.ceremony_fragment));
        titles.add(activity.getString(R.string.design_fragment));
        return titles;
    }
}
