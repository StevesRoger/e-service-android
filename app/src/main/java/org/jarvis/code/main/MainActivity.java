package org.jarvis.code.main;

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
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.ViewPagerAdapter;
import org.jarvis.code.core.fragment.CeremonyFragment;
import org.jarvis.code.core.fragment.DesignFragment;
import org.jarvis.code.core.fragment.WeddingFragment;
import org.jarvis.code.util.Constant;
import org.jarvis.code.util.ImageAnimate;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView adImage;
    private int adImages[] = {R.drawable.coca_col_ad,
            R.drawable.hi_tea_ad, R.drawable.samsung_ad, R.drawable.v_printing_ad};
    private Random random = new Random();
    private ScheduledExecutorService scheduleTaskExecutor;
    private int resId = R.drawable.samsung_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkRunTimePermission();
        init();
        ImageAnimate.animate(adImage,adImages,0,true);
        //scheduleTaskAD();
    }

    private void init() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new WeddingFragment(), getResources().getString(R.string.wedding_fragment));
        viewPagerAdapter.addFragment(new CeremonyFragment(), getResources().getString(R.string.ceremony_fragment));
        viewPagerAdapter.addFragment(new DesignFragment(), getResources().getString(R.string.design_fragment));
        viewPager = (ViewPager) findViewById(R.id.tabPager);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        adImage = (ImageView) findViewById(R.id.imgAd);
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
            int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                else
                    ActivityCompat.requestPermissions(this, Constant.PERMISSIONS_STORAGE, Constant.REQUEST_PERMISSIONS_CODE);
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
                    Log.e(Constant.TAG, "Permission Granted, Now you can use local drive .");
                else
                    Log.e(Constant.TAG, "Permission Denied, You cannot use local drive .");
                break;
        }
    }
}
