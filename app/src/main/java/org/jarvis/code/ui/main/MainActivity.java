package org.jarvis.code.ui.main;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jarvis.code.R;
import org.jarvis.code.adapter.TabAdapter;
import org.jarvis.code.receive.FCMReceiver;
import org.jarvis.code.ui.base.AbstractActivity;
import org.jarvis.code.ui.product.ProductFragment;
import org.jarvis.code.util.AnimateAD;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AbstractActivity implements MainView, FCMReceiver.IReceiver {

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

    private FCMReceiver fcmReceiver;
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

        fcmReceiver = new FCMReceiver(this);

        FirebaseMessaging.getInstance().subscribeToTopic("V-Printing");
        FirebaseInstanceId.getInstance().getToken();

        Loggy.i(MainActivity.class, "register receiver");
        localBroadcastManager.registerReceiver(fcmReceiver, new IntentFilter(Constants.FCM_BROADCAST_ACTION));

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
        localBroadcastManager.unregisterReceiver(fcmReceiver);
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onReceive(Context context, JSONArray jsonArray) {
        Loggy.i(MainActivity.class, jsonArray.toString());
    }


    public void onRefreshAD() {
        //requestClient.fetchAdvertisement().enqueue(this);
    }

    @Override
    public void startAnimateAD(List<Integer> ads) {
        if (!isLoad) {
            AnimateAD.animate(imageAd, ads, 0, true, this);
            isLoad = true;
        }
    }
}
