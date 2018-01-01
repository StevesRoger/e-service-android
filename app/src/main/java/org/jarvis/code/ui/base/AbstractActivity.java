package org.jarvis.code.ui.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.VPrintApplication;
import org.jarvis.code.dagger.component.ActivityComponent;
import org.jarvis.code.dagger.component.DaggerActivityComponent;
import org.jarvis.code.dagger.module.ActivityModule;
import org.jarvis.code.service.FirebaseBroadcastReceiver;
import org.jarvis.code.ui.main.MainActivity;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ki.kao on 10/4/2017.
 */

public abstract class AbstractActivity extends AppCompatActivity implements BaseView, AbstractFragment.Callback {

    private ActivityComponent activityComponent;
    private Unbinder unbinder;
    protected LocalBroadcastManager localBroadcastManager;
    protected FirebaseBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(VPrintApplication.get(this).getComponent())
                .build();
        checkRunTimePermission();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unbinder = unBinder;
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null)
            unbinder.unbind();
        if (receiver != null)
            localBroadcastManager.unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    Toast.makeText(this, "Write External Storage permission allows us to do store image. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                else {
                    ActivityCompat.requestPermissions(this, Constants.MY_PERMISSIONS, Constants.REQUEST_PERMISSIONS_CODE);
                    Loggy.i(MainActivity.class, "Request permission");
                }
            } else {
                Loggy.i(MainActivity.class, "Permission is granted");
            }
        } else {
            Loggy.i(MainActivity.class, "Build version smaller than API23");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Loggy.e(MainActivity.class, "Permission Granted, Now you can use local drive.");
                else
                    Loggy.e(MainActivity.class, "Permission Denied, You cannot use local drive.");
                break;
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void alertMessage(String message, int duration) {
        if (message != null) {
            Toast.makeText(this, message, duration).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), duration).show();
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void showSweetAlert(int type, String title, String content) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, type)
                .setTitleText(title)
                .setContentText(content);
        //dialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}
