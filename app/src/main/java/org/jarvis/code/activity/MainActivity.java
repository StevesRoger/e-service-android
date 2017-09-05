package org.jarvis.code.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.FragmentAdapter;
import org.jarvis.code.core.fragment.IFragment;
import org.jarvis.code.core.fragment.ProductFragment;
import org.jarvis.code.util.Constant;
import org.jarvis.code.util.AdvertisementUtil;
import org.jarvis.code.util.Jog;
import org.jarvis.code.util.RetrofitUtil;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter viewPagerAdapter;
    private ImageView adImage;
    private SearchView searchView;

    private int adImages[] = {R.drawable.coca_col_ad,
            R.drawable.hi_tea_ad, R.drawable.samsung_ad, R.drawable.v_printing_ad};

    private Random random = new Random();
    private ScheduledExecutorService scheduleTaskExecutor;
    private int resId = R.drawable.samsung_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //new AdvertisementUtil(getApplicationContext(), adImage);
        checkRunTimePermission();
        FirebaseMessaging.getInstance().subscribeToTopic("Testing");
        FirebaseInstanceId.getInstance().getToken();
        //scheduleTaskAD();
    }

    private void init() {

        viewPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ProductFragment.newInstance("WED"), getResources().getString(R.string.wedding_fragment));
        viewPagerAdapter.addFragment(ProductFragment.newInstance("CER"), getResources().getString(R.string.ceremony_fragment));
        viewPagerAdapter.addFragment(ProductFragment.newInstance("DES"), getResources().getString(R.string.design_fragment));

        viewPager = (ViewPager) findViewById(R.id.tabPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        adImage = (ImageView) findViewById(R.id.imgAd);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setQueryHint(getResources().getString(R.string.string_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((IFragment) viewPagerAdapter.getItem(viewPager.getCurrentItem())).search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((IFragment) viewPagerAdapter.getItem(viewPager.getCurrentItem())).search(newText);
                return true;
            }
        });
    }

    private void scheduleTaskAD() {
        /*scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                resId = random.nextInt(adImages.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adImage.setImageResource(adImages[resId]);
                    }
                });
            }
        }, 0, 15, TimeUnit.SECONDS);*/
    }

    private void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                else
                    ActivityCompat.requestPermissions(this, Constant.MY_PERMISSIONS, Constant.REQUEST_PERMISSIONS_CODE);
            }
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQUEST_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Jog.e(MainActivity.class, "Permission Granted, Now you can use local drive .");
                else
                    Jog.e(MainActivity.class, "Permission Denied, You cannot use local drive .");
                break;
        }
    }
}
