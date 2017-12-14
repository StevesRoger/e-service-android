package org.jarvis.code.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import org.jarvis.code.dagger.component.ActivityComponent;
import org.jarvis.code.service.FirebaseBroadcastReceiver;

import butterknife.Unbinder;

/**
 * Created by ki.kao on 10/5/2017.
 */

public abstract class AbstractFragment extends Fragment implements BaseView {

    private AbstractActivity activity;
    private Unbinder unbinder;
    protected FirebaseBroadcastReceiver receiver;
    protected LocalBroadcastManager localBroadcastManager;

    public AbstractFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AbstractActivity) {
            this.activity = (AbstractActivity) context;
            this.activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        this.activity = null;
        if (receiver != null)
            localBroadcastManager.unregisterReceiver(receiver);
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        if (unbinder != null)
            unbinder.unbind();
        if (receiver != null)
            localBroadcastManager.unregisterReceiver(receiver);
        super.onDestroyView();
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null)
            return activity.isNetworkConnected();
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (activity != null)
            activity.hideKeyboard();
    }

    @Override
    public void showMessage(String message, int duration) {
        if (activity != null)
            activity.showMessage(message, duration);
    }

    @Override
    public void showSnackBar(String message) {
        if (activity != null)
            activity.showSnackBar(message);
    }

    @Override
    public void showSweetAlert(int type, String title, String content) {
        if (activity != null)
            activity.showSweetAlert(type, title, content);
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null)
            return activity.getActivityComponent();
        return null;
    }

    public AbstractActivity getBaseActivity() {
        return activity;
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unbinder = unBinder;
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
