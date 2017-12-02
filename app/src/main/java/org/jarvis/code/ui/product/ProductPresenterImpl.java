package org.jarvis.code.ui.product;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.model.Product;
import org.jarvis.code.model.Promotion;
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

    private PromotionInteractorImpl promotionInteractor;

    @Inject
    public ProductPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
        this.interactor = new ProductInteractorImpl(this);
        this.promotionInteractor = new PromotionInteractorImpl(this);

    }

    @Override
    public void loadProduct(int limit, String type) {
        requestClient.fetchProducts(1, limit, type).enqueue(interactor);
    }

    @Override
    public void loadMoreProduct() {
        if (view != null)
            view.loadMoreProduct();
    }

    @Override
    public void onLoadMoreProduct(int offset, int limit, String type) {
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
    public void loadPromotion(int offset, int limit) {
        requestClient.fetchPromotions(offset, limit).enqueue(promotionInteractor);
    }

    @Override
    public void onLoadPromotionSucceed(List<Promotion> promotions) {
        if (view != null)
            view.loadPromotionSucceed(promotions);
    }

    @Override
    public void onLoadPromotionFailure(String message) {
        if (view != null)
            view.toastMessage(message);
    }

    @Override
    public void refreshView() {
        if (view != null)
            view.updateView();
    }

    @Override
    public void updateProduct(Product product) {
        if (view != null)
            view.updateProduct(product);
    }


    public PromotionInteractorImpl getPromotionInteractor() {
        return promotionInteractor;
    }
}
