package org.jarvis.code.ui.product.promotion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.jarvis.code.dagger.ActivityContext;
import org.jarvis.code.model.read.Promotion;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.ui.base.BasePresenterImpl;
import org.jarvis.code.ui.product.ProductView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ki.kao on 10/11/2017.
 */

public class PromotionPresenterImpl extends BasePresenterImpl<ProductView> implements PromotionPresenter<ProductView> {

    @Inject
    public PromotionPresenterImpl(AppCompatActivity activity, @ActivityContext Context context, RequestClient requestClient) {
        super(activity, context, requestClient);
        this.interactor = new PromotionInteractorImpl(this);
    }

    @Override
    public void loadPromotion(int offset, int limit) {
        requestClient.fetchPromotions(offset, limit).enqueue(interactor);
    }

    @Override
    public void onLoadPromotionSucceed(List<Promotion> promotions) {
        if (view != null)
            view.loadPromotionSucceed(promotions);
    }
}
