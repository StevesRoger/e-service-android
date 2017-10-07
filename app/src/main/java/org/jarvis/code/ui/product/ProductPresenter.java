package org.jarvis.code.ui.product;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.ui.base.BasePresenter;
import org.jarvis.code.ui.base.BaseView;

import java.util.List;

/**
 * Created by ki.kao on 10/5/2017.
 */
@PerActivity
public interface ProductPresenter<V extends BaseView> extends BasePresenter<V> {

    void loadProduct(int limit, String type);

    void loadMoreProduct(int offset, int limit, String type);

    void onLoadProductSuccess(List<Product> products);

    void onLoadProductFailure(String message);
}
