package org.jarvis.code.ui.product;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.util.Loggy;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/5/2017.
 */

public class ProductInteractorImpl extends RecyclerView.OnScrollListener implements ProductInteractor<Product> {

    private ProductPresenter<ProductView> presenter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    public ProductInteractorImpl(ProductPresenter<ProductView> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (presenter != null && response.isSuccessful())
            presenter.onLoadProductSuccess(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        if (presenter != null)
            presenter.onLoadProductFailure(t.getMessage());
    }

    public void loaded() {
        isLoading = false;
    }

    @Override
    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
            if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (presenter != null) {
                    presenter.onLoadMoreProduct();
                    isLoading = true;
                }
            }
            Loggy.i(ProductInteractorImpl.class, "Scrolling up");
            Loggy.i(ProductInteractorImpl.class, dx + " " + dy);
        } else {
            Loggy.i(ProductInteractorImpl.class, "Scrolling down");
            Loggy.i(ProductInteractorImpl.class, dx + " " + dy);
        }
    }
}
