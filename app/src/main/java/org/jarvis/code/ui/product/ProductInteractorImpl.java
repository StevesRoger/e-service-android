package org.jarvis.code.ui.product;

import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.ui.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/5/2017.
 */

public class ProductInteractorImpl extends AbstractInteractor<Product> implements ProductInteractor<Product> {

    private ProductPresenter<ProductView> presenter;

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


}
