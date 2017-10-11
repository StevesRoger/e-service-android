package org.jarvis.code.ui.product;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ProductAdapter;
import org.jarvis.code.dagger.component.ActivityComponent;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.read.Promotion;
import org.jarvis.code.ui.base.AbstractFragment;
import org.jarvis.code.ui.product.promotion.PromotionPresenter;
import org.jarvis.code.util.Loggy;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ki.kao on 9/1/2017.
 */

public class ProductFragment extends AbstractFragment implements ProductView {

    @BindView(R.id.recycler_view_product)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.txt_message)
    TextView lblMessage;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    ProductAdapter adapter;
    @Inject
    ProductPresenter<ProductView> productPresenter;
    @Inject
    PromotionPresenter<ProductView> promotionPresenter;

    private boolean isLoaded = false;
    private boolean isVisibleToUser;
    private String type;
    private final int LIMIT = 5;
    private int offset = 1;

    private int position = 5;
    private int page = 1;

    public static ProductFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments() != null ? getArguments().getString("type") : "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            productPresenter.onAttach(this);
            productPresenter.getInteractor().setLinearLayoutManager(linearLayoutManager);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isVisibleToUser && (!isLoaded)) {
            productPresenter.loadProduct(LIMIT, type);
            isLoaded = true;
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(productPresenter.getInteractor());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isAdded()) {
            productPresenter.loadProduct(LIMIT, type);
            isLoaded = true;
        }
    }

    @Override
    public void onRefresh() {
        //mainActivity.onRefreshAD();
        productPresenter.loadProduct(LIMIT, type);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadMoreProduct() {
        Loggy.i(ProductFragment.class, type + " On load more product");
        adapter.add(null);
        recyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyItemInserted(adapter.size() - 1);
                productPresenter.LoadMoreProduct(offset, LIMIT, type);
            }
        });
    }

    @Override
    public void loadMoreProductSucceed(List<Product> products) {
        adapter.remove(adapter.size() - 1);
        adapter.notifyItemRemoved(adapter.size());
        Loggy.i(ProductFragment.class, type + " On load more success");
        Loggy.i(ProductFragment.class, products.toString());
        if (!products.isEmpty()) {
            adapter.addAll(products);
            promotionPresenter.loadPromotion(page,1);
            adapter.notifyDataSetChanged();
            offset++;
            productPresenter.getInteractor().loaded();
        }
    }

    @Override
    public void loadMoreProductFailed(String message) {
        Loggy.e(ProductFragment.class, type + " On load more failure");
        Loggy.e(ProductFragment.class, message);
        toastMessage(message);
        adapter.remove(adapter.size() - 1);
        adapter.notifyItemRemoved(adapter.size());
    }

    @Override
    public void search(String text) {
        adapter.filter(text.toLowerCase());
    }

    @Override
    public void loadPromotionSucceed(List<Promotion> promotions) {
        if (promotions != null && !promotions.isEmpty()) {
            adapter.add(position, promotions.get(0));
            Loggy.i(ProductFragment.class, type + " advertisement position:" + position);
            position = (position + 5) + 1;
            page = (position - 1) / 5;
        }
    }


    /* private void fetchPromotion(final int step) {
        Loggy.i(ProductFragment.class, type + " advertisement offset:" + step);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Response<ResponseEntity<Promotion>> response = requestService.fetchPromotions(step, 1).execute();
                    if (response.code() == 200) {
                        ResponseEntity<Promotion> body = response.body();
                        if (body != null && !body.getData().isEmpty()) {
                            adapter.add(position, body.getData().get(0));
                            Loggy.i(ProductFragment.class, type + " advertisement position:" + position);
                            position = (position + 5) + 1;
                            page = (position - 1) / 5;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Loggy.e(ProductFragment.class, e.getMessage());
                }
                return null;
            }
        }.execute();
    }*/

    @Override
    public void loadProductSucceed(List<Product> products) {
        Loggy.i(ProductFragment.class, products.toString());
        productPresenter.getInteractor().loaded();
        adapter.clear();
        adapter.addAll(products);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        offset = 2;
        position = 5;
        page = 1;
        /*if (adapter.size() == 5)
            fetchPromotion(page);*/
    }

    @Override
    public void loadProductFailed(String message) {
        Loggy.e(ProductFragment.class, message);
        showErrorMessage();
        swipeRefreshLayout.setRefreshing(false);
        toastMessage(message);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        lblMessage.setText("Oop...There are somethings went wrong!");
        lblMessage.setTextColor(Color.parseColor("#f80606"));
        lblMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void noProductAvailable() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        lblMessage.setText("There is no product available on server!");
        lblMessage.setTextColor(Color.parseColor("#141313"));
        lblMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        productPresenter.onDetach();
        super.onDestroyView();
    }

}
