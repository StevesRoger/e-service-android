package org.jarvis.code.ui.product;

import org.jarvis.code.model.read.Product;
import org.jarvis.code.ui.base.BaseView;

import java.util.List;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface ProductView extends BaseView {

    void loadProductSucceed(List<Product> products);

    void loadProductFailed(String message);
}
