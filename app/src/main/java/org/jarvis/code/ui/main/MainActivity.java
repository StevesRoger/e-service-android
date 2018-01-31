package org.jarvis.code.ui.main;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jarvis.code.R;
import org.jarvis.code.adapter.TabAdapter;
import org.jarvis.code.service.FirebaseBroadcastReceiver;
import org.jarvis.code.ui.base.AbstractActivity;
import org.jarvis.code.ui.product.ProductFragment;
import org.jarvis.code.util.Animator;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import javax.inject.Inject;

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

    private boolean isLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
        if (isNetworkAvailable()) {
            presenter.fetchAdvertisement();
            presenter.fetchPromotion();
        } else
            alertMessage("No internet access", Toast.LENGTH_SHORT);

        tabAdapter.addFragment(ProductFragment.newInstance("GEN"));
        tabAdapter.addFragment(ProductFragment.newInstance("WED"));
        tabAdapter.addFragment(ProductFragment.newInstance("DES"));

        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);

        searchView.setQueryHint(getString(R.string.search_product));
        searchView.setOnQueryTextListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("V-Printing");
        FirebaseInstanceId.getInstance().getToken();

        Loggy.i(MainActivity.class, "register receiver");
        receiver = new FirebaseBroadcastReceiver(presenter.getInteractor());
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.FCM_BROADCAST_ADVERTISEMENT));
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
        ProductFragment fragment = (ProductFragment) presenter.getCurrentFragment(viewPager.getCurrentItem());
        fragment.search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ProductFragment fragment = (ProductFragment) presenter.getCurrentFragment(viewPager.getCurrentItem());
        fragment.search(newText);
        return true;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void refresh() {
        presenter.fetchAdvertisement();
        presenter.fetchPromotion();
    }

    @Override
    public void startAnimateAD() {
        if (!isLoad && !Constants.advertisement.isEmpty()) {
            Animator.animateAD(imageAd, this, 0, Constants.advertisement, true);
            isLoad = true;
        }
    }
}
