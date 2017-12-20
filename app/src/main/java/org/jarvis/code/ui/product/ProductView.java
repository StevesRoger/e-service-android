package org.jarvis.code.ui.product;

import android.support.v4.widget.SwipeRefreshLayout;

import org.jarvis.code.model.Product;
import org.jarvis.code.model.Promotion;
import org.jarvis.code.ui.base.BaseView;

import java.util.List;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface ProductView extends BaseView, SwipeRefreshLayout.OnRefreshListener {

    void loadProductSucceed(List<Product> products);

    void loadProductFailed(String message);

    void loadMoreProduct();

    void loadMoreProductSucceed(List<Product> products);

    void loadMoreProductFailed(String message);

    void showProgress();

    void hideProgress();

    void showErrorMessage(String message);

    void noProductAvailable();

    void search(String text);

    void notifyDataSetChanged();

    void updateProduct(Product product);
}
