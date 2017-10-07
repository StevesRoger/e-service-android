package org.jarvis.code.core.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.ui.product.ProductFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ki.kao on 9/1/2017.
 */

public interface IFragment<T> {

    void onLoadMore();

    void onLoadMoreSuccess(Call<ResponseEntity<T>> call, Response<ResponseEntity<T>> response);

    void onLoadMoreFailure(Call<ResponseEntity<T>> call, Throwable t);


}
