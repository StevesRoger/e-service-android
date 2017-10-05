package org.jarvis.code.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import org.jarvis.code.dagger.component.ActivityComponent;

import butterknife.Unbinder;

/**
 * Created by ki.kao on 10/5/2017.
 */

public abstract class AbstractFragment extends Fragment implements BaseView {

    private AbstractActivity activity;
    private Unbinder unbinder;

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
        super.onDetach();

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
    public void showMessage(String message) {
        if (activity != null)
            activity.showMessage(message);
    }

    @Override
    public void showSnackBar(String message) {
        if (activity != null)
            activity.showSnackBar(message);
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
