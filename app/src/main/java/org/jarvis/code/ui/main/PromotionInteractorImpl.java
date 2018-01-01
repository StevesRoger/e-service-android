package org.jarvis.code.ui.main;

import android.content.Context;

import org.jarvis.code.model.Promotion;
import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by KimChheng on 10/16/2017.
 */

public class PromotionInteractorImpl implements PromotionInteractor<Promotion> {

    private MainPresenter<MainView> presenter;

    public PromotionInteractorImpl(MainPresenter<MainView> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNewItem(Context context, JSONArray jsonArray) throws Exception {

    }

    @Override
    public void onUpdateItem(Context context, JSONArray jsonArray) throws Exception {

    }

    @Override
    public void onDeleteItem(Context context, JSONArray jsonArray) throws Exception {

    }

    @Override
    public void onResponse(Call<ResponseEntity<Promotion>> call, Response<ResponseEntity<Promotion>> response) {
        if (response.code() == 200) {
            List<Promotion> list = response.body().getData();
            for (Promotion promotion : list) {
                if (!Constants.promotion.containsKey(promotion.getId()))
                    Constants.promotion.put(promotion.getId(), promotion);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Promotion>> call, Throwable t) {
        Loggy.i(PromotionInteractorImpl.class, t.getMessage());
    }
}
