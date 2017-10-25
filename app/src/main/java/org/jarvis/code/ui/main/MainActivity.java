package org.jarvis.code.ui.main;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.ArrayMap;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jarvis.code.R;
import org.jarvis.code.adapter.TabAdapter;
import org.jarvis.code.service.FirebaseBroadcastReceiver;
import org.jarvis.code.ui.base.AbstractActivity;
import org.jarvis.code.ui.product.ProductFragment;
import org.jarvis.code.util.AnimateAD;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AbstractActivity implements MainView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_pager)
    ViewPager viewPager;
    @BindView(R.id.img_ad)
    ImageView imageAd;
    @BindView(R.id.search_view)
    SearchView searchView;
    @Inject
    TabAdapter tabAdapter;
    @Inject
    MainPresenter<MainView> presenter;
    @Inject
    LocalBroadcastManager localBroadcastManager;
    @Inject
    ArrayMap<Integer, Integer> advertisement;
    @Inject
    @Named("advertisement")
    FirebaseBroadcastReceiver advertisementReceiver;

    private boolean isLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
        presenter.fetchAdvertisement();

        tabAdapter.addFragment(ProductFragment.newInstance("WED"));
        tabAdapter.addFragment(ProductFragment.newInstance("CER"));
        tabAdapter.addFragment(ProductFragment.newInstance("DES"));

        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);

        searchView.setQueryHint(getString(R.string.string_search_hint));
        searchView.setOnQueryTextListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("V-Printing");
        FirebaseInstanceId.getInstance().getToken();

        Loggy.i(MainActivity.class, "register receiver");
        localBroadcastManager.registerReceiver(advertisementReceiver, new IntentFilter(Constants.FCM_BROADCAST_PRODUCT));

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ((ProductFragment) presenter.getCurrentFragment(viewPager.getCurrentItem())).search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((ProductFragment) presenter.getCurrentFragment(viewPager.getCurrentItem())).search(newText);
        return true;
    }

    @Override
    protected void onDestroy() {
        Loggy.i(MainActivity.class, "unregister receiver");
        localBroadcastManager.unregisterReceiver(advertisementReceiver);
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void refreshAD() {
        if (advertisement.isEmpty()) {
            presenter.fetchAdvertisement();
        }
    }

    @Override
    public void startAnimateAD() {
        if (!isLoad && !advertisement.isEmpty()) {
            AnimateAD.animate(imageAd, advertisement, 0, true, this);
            isLoad = true;
        }
    }
}
