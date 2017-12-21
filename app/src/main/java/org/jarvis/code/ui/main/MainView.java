package org.jarvis.code.ui.main;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;

import org.jarvis.code.ui.base.BaseView;

import java.util.List;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface MainView extends BaseView, ActivityCompat.OnRequestPermissionsResultCallback, SearchView.OnQueryTextListener {

    void startAnimateAD();

    void refresh();
}
