package org.jarvis.code.ui.product;

import android.content.Context;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/5/2017.
 */

public class ProductPresenterImpl extends BasePresenterImpl<ProductView> implements ProductPresenter<ProductView> {

    private ProductInteractor<Product> interactor;

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
    public void loadMoreProduct(int offset, int limit, String type) {

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
}
