package org.jarvis.code.ui.product;

import android.support.v7.widget.LinearLayoutManager;

import org.jarvis.code.ui.base.BaseInteractor;

/**
 * Created by ki.kao on 10/5/2017.
 */

public interface ProductInteractor<T> extends BaseInteractor<T> {

    void setLinearLayoutManager(LinearLayoutManager linearLayoutManager);
}
