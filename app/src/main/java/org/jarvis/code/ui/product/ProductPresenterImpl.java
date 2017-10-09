package org.jarvis.code.ui.product;

import android.content.Context;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/5/2017.
 */

public class ProductPresenterImpl extends BasePresenterImpl<ProductView> implements ProductPresenter<ProductView> {

    @Inject
    public ProductPresenterImpl(@ActivityContext Context context, RequestClient requestClient) {
        super(context, requestClient);
        this.interactor = new ProductInteractorImpl(this);
    }

    @Override
    public void loadProduct(int limit, String type) {
        requestClient.fetchProducts(1, limit, type).enqueue(interactor);
    }

    @Override
    public void LoadMoreProduct(int offset, int limit, String type) {
        requestClient.fetchProducts(offset, limit, type).enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if (view != null && response.isSuccessful())
                    view.loadMoreProductSucceed(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                if (view != null)
                    view.loadMoreProductFailed(t.getMessage());
            }
        });
    }

    @Override
    public void onLoadMoreProduct() {
        if (view != null)
            view.loadMoreProduct();
    }

    @Override
    public void onLoadProductSuccess(List<Product> products) {
        if (view != null) {
            if (products != null) {
                if (!products.isEmpty()) {
                    view.loadProductSucceed(products);
                    view.hideProgress();
                } else
                    view.noProductAvailable();
            } else
                view.showErrorMessage();
        }
    }

    @Override
    public void onLoadProductFailure(String message) {
        if (view != null)
            view.loadProductFailed(message);
    }

    @Override
    public ProductInteractorImpl getInteractor() {
        return (ProductInteractorImpl) interactor;
    }

}
