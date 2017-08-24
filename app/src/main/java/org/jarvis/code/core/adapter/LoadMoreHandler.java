package org.jarvis.code.core.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jarvis.code.util.Constant;

/**
 * Created by ki.kao on 8/24/2017.
 */

public class LoadMoreHandler extends RecyclerView.OnScrollListener {

    private RecyclerView recyclerView;
    private LoadMoreListener loadMoreListener;

    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public LoadMoreHandler(LoadMoreListener loadMoreListener, RecyclerView recyclerView) {
        this.loadMoreListener = loadMoreListener;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager linearLayoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();
        totalItemCount = linearLayoutManager.getItemCount();
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore();
                Log.i(Constant.TAG, "LoadMoreHandler.onLoadMore");
            }
            loading = true;
        }
    }

    public void loaded() {
        loading = false;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
