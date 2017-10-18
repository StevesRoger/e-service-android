package org.jarvis.code.ui.product;

import android.content.Context;

import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.model.read.Promotion;
import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by KimChheng on 10/16/2017.
 */

public class PromotionInteractorImpl implements PromotionInteractor<Promotion> {

    private ProductPresenter presenter;

    public PromotionInteractorImpl(ProductPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNewItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onUpdateItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onDeleteItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onResponse(Call<ResponseEntity<Promotion>> call, Response<ResponseEntity<Promotion>> response) {
        if (presenter != null && response.isSuccessful())
            presenter.onLoadPromotionSucceed(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseEntity<Promotion>> call, Throwable t) {
        if (presenter != null)
            presenter.onLoadPromotionFailure(t.getMessage());
    }
}
