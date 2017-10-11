package org.jarvis.code.ui.product.promotion;

import org.jarvis.code.model.read.Promotion;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.ui.product.ProductView;
import org.jarvis.code.util.Loggy;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/11/2017.
 */

public class PromotionInteractorImpl implements PromotionInteractor<Promotion> {

    private PromotionPresenter<ProductView> presenter;

    public PromotionInteractorImpl(PromotionPresenter<ProductView> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Promotion>> call, Response<ResponseEntity<Promotion>> response) {
        if (presenter != null && response.isSuccessful())
            presenter.onLoadPromotionSucceed(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseEntity<Promotion>> call, Throwable t) {
        Loggy.e(PromotionInteractorImpl.class,t.getMessage());
    }
}
