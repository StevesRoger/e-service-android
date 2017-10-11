package org.jarvis.code.ui.product.promotion;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.read.Promotion;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.product.ProductView;

import java.util.List;

/**
 * Created by ki.kao on 10/11/2017.
 */
@PerActivity
public interface PromotionPresenter<V extends ProductView> extends BasePresenter<V> {

    void loadPromotion(int offset, int limit);

    void onLoadPromotionSucceed(List<Promotion> promotions);
}
