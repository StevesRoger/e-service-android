package org.jarvis.code.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.jarvis.code.R;
import org.jarvis.code.ui.base.AbstractActivity;
import org.jarvis.code.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by KimChheng on 7/16/2017.
 */

public class SplashActivity extends AbstractActivity implements SplashView {

    private static boolean splashLoaded = false;

    @Inject
    SplashPresenter<SplashView> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1 * 500);

            splashLoaded = true;
        } else {
            openMainActivity();
        }
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
           /*final Picasso picasso = new Picasso.Builder(SplashActivity.this)
                .downloader(new OkHttp3Downloader(RequestFactory.okHttpClient))
                .build();
        Picasso.setSingletonInstance(picasso);*/
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void openLoginActivity() {

    }

    @Override
    public void openMainActivity() {
        Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
        goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(goToMainActivity);
        finish();
    }
}
