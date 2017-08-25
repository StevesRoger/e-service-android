package org.jarvis.code.core.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jarvis.code.util.Constant;

/**
 * Created by ki.kao on 8/24/2017.
 */

public class LoadMoreHandler extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private LoadMoreListener loadMoreListener;
    private boolean isLoading = true;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    public LoadMoreHandler(LoadMoreListener loadMoreListener, RecyclerView recyclerView) {
        this.loadMoreListener = loadMoreListener;
        this.linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
            if (isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
                isLoading = false;
            }
            Log.i(Constant.TAG, "Scrolling up");
        } else {
            Log.i(Constant.TAG, "Scrolling down");
        }
    }

    public void loaded() {
        isLoading = true;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
