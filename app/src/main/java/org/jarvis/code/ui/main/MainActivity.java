package org.jarvis.code.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.TabAdapter;
import org.jarvis.code.core.fragment.IFragment;
import org.jarvis.code.core.fragment.ProductFragment;
import org.jarvis.code.receive.FCMReceiver;
import org.jarvis.code.ui.base.BaseActivity;
import org.jarvis.code.util.AnimateAD;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, FCMReceiver.IReceiver {

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
        tabAdapter.addFragment(ProductFragment.newInstance("WED", this), getResources().getString(R.string.wedding_fragment));
        tabAdapter.addFragment(ProductFragment.newInstance("CER", this), getResources().getString(R.string.ceremony_fragment));
        tabAdapter.addFragment(ProductFragment.newInstance("DES", this), getResources().getString(R.string.design_fragment));

        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);

        searchView.setQueryHint(getResources().getString(R.string.string_search_hint));
        searchView.setOnQueryTextListener(this);

        fcmReceiver = new FCMReceiver(this);

        checkRunTimePermission();
        FirebaseMessaging.getInstance().subscribeToTopic("V-Printing");
        FirebaseInstanceId.getInstance().getToken();
        Loggy.i(MainActivity.class, "register receiver");
        LocalBroadcastManager.getInstance(this).registerReceiver(fcmReceiver, new IntentFilter(Constants.FCM_BROADCAST_ACTION));
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
        ((IFragment) tabAdapter.getItem(viewPager.getCurrentItem())).search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((IFragment) tabAdapter.getItem(viewPager.getCurrentItem())).search(newText);
        return true;
    }

    @Override
    protected void onStop() {
        Loggy.i(MainActivity.class, "unregister receiver");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcmReceiver);
        super.onStop();
    }

    @Override
    public void onReceive(Context context, JSONArray jsonArray) {
        Loggy.i(MainActivity.class, jsonArray.toString());
    }

    private void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    Toast.makeText(this, "Write External Storage permission allows us to do store image. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                else {
                    ActivityCompat.requestPermissions(this, Constants.MY_PERMISSIONS, Constants.REQUEST_PERMISSIONS_CODE);
                    Loggy.i(MainActivity.class, "Request permission");
                }
            } else {
                Loggy.i(MainActivity.class, "Permission is granted");
            }
        } else {
            Loggy.i(MainActivity.class, "Permission is granted");
        }
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
