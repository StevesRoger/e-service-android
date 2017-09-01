package org.jarvis.code.core.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import org.jarvis.code.core.model.response.ResponseEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ki.kao on 9/1/2017.
 */

public interface IFragment<T> extends Callback<ResponseEntity<T>>, SwipeRefreshLayout.OnRefreshListener {

    void search(String text);

    void onLoadMore();

    void onLoadMoreSuccess(Call<ResponseEntity<T>> call, Response<ResponseEntity<T>> response);

    void onLoadMoreFailure(Call<ResponseEntity<T>> call, Throwable t);


}
