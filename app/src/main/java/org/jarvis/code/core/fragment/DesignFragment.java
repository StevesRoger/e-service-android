package org.jarvis.code.core.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.api.RequestService;
import org.jarvis.code.core.adapter.FilterProduct;
import org.jarvis.code.core.adapter.LoadMoreHandler;
import org.jarvis.code.core.adapter.ProductAdapter;
import org.jarvis.code.core.model.response.Product;
import org.jarvis.code.core.model.response.Promotion;
import org.jarvis.code.core.model.response.ResponseEntity;
import org.jarvis.code.util.Constant;
import org.jarvis.code.util.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KimChheng on 6/4/2017.
 */

public class DesignFragment extends Fragment implements Callback<ResponseEntity<Product>>, LoadMoreHandler.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener, FilterProduct {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private RequestService requestService;
    private TextView lblMessage;

    private ProductAdapter adapter;
    private List<Product> products;
    private LoadMoreHandler loadMoreHandler;

    private static final int LIMIT = 5;
    private int offset = 1;
    private int promotion = 5;

    public DesignFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), products);
        requestService = RequestFactory.build(RequestService.class);
        requestService.fetchProducts(1, LIMIT, "DES").enqueue(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!products.isEmpty())
            progressBar.setVisibility(View.GONE);
        recyclerView.addOnScrollListener(loadMoreHandler = new LoadMoreHandler(this, recyclerView));
        Log.i(Constant.TAG, "WeddingFragment.onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desgin_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        lblMessage = (TextView) view.findViewById(R.id.txtMessage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProduct);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            ResponseEntity<Product> responseEntity = response.body();
            Log.i(Constant.TAG, responseEntity.getData().toString());
            loadMoreHandler.loaded();
            adapter.clear();
            adapter.addAll(responseEntity.getData());
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            offset = 1;
            promotion = 5;
            if (products.isEmpty() && lblMessage.getVisibility() == View.GONE) {
                lblMessage.setText("There is no product from server!");
                lblMessage.setVisibility(View.VISIBLE);
            } else
                lblMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Log.e(Constant.TAG, t.getMessage());
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore() {
        if (offset == 1) offset++;
        adapter.add(null);
        recyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyItemInserted(products.size() - 1);
                requestService.fetchProducts(offset, LIMIT, "DES").enqueue(new Callback<ResponseEntity<Product>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                        onLoadMoreSuccess(call, response);
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                        onLoadMoreFailure(call, t);
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        requestService.fetchProducts(1, LIMIT, "DES").enqueue(this);
    }

    private void onLoadMoreSuccess(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            adapter.remove(products.size() - 1);
            adapter.notifyItemRemoved(products.size());
            List<Product> list = response.body().getData();
            if (!list.isEmpty()) {
                for (Product product : list)
                    adapter.add(product);
                adapter.notifyDataSetChanged();
                offset++;
                loadMoreHandler.loaded();
                if (products.size() >= 5)
                    getPromotion(products.get(promotion));
            }
        }
    }

    private void onLoadMoreFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Log.e(Constant.TAG, t.getMessage());
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        adapter.remove(products.size() - 1);
        adapter.notifyItemRemoved(products.size());
    }

    private void getPromotion(final Product product) {
        final int offset = promotion / 5;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Response<ResponseEntity<Promotion>> response = requestService.fetchPromotions(offset, 1).execute();
                    if (response.code() == 200) {
                        ResponseEntity<Promotion> body = response.body();
                        if (body != null && !body.getData().isEmpty()) {
                            product.setPromotion(body.getData().get(0));
                            promotion = promotion + 5;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(Constant.TAG, e.getMessage());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void search(String text) {
        adapter.filter(text);
    }
}
