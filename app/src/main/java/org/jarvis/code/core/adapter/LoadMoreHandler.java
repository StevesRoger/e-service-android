package org.jarvis.code.core.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jarvis.code.core.fragment.IFragment;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.util.Loggy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ki.kao on 8/24/2017.
 */

public class LoadMoreHandler<T> extends RecyclerView.OnScrollListener implements Callback<ResponseEntity<T>> {

    private LinearLayoutManager linearLayoutManager;
    private IFragment<T> iFragment;
    private boolean isLoading;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    public LoadMoreHandler(IFragment<T> iFragment, RecyclerView recyclerView) {
        this.iFragment = iFragment;
        this.linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
            if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (iFragment != null) {
                    iFragment.onLoadMore();
                    isLoading = true;
                }
            }
            Loggy.i(LoadMoreHandler.class, "Scrolling up");
            Loggy.i(LoadMoreHandler.class, dx + " " + dy);
        } else {
            Loggy.i(LoadMoreHandler.class, "Scrolling down");
            Loggy.i(LoadMoreHandler.class, dx + " " + dy);
        }
    }

    public void loaded() {
        isLoading = false;
    }

    @Override
    public void onResponse(Call<ResponseEntity<T>> call, Response<ResponseEntity<T>> response) {
        iFragment.onLoadMoreSuccess(call, response);
    }

    @Override
    public void onFailure(Call<ResponseEntity<T>> call, Throwable t) {
        iFragment.onLoadMoreFailure(call, t);
    }
}
