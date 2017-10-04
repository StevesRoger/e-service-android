package org.jarvis.code.ui.base;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface IView extends ActivityCompat.OnRequestPermissionsResultCallback, SearchView.OnQueryTextListener {

    boolean isNetworkConnected();

    void hideKeyboard();
}
