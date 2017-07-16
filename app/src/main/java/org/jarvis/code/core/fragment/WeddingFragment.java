package org.jarvis.code.core.fragment;

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
import org.jarvis.code.api.WeddingApi;
import org.jarvis.code.core.adapter.WeddingAdapter;
import org.jarvis.code.core.model.response.Product;
import org.jarvis.code.core.model.response.ResponseEntity;
import org.jarvis.code.util.Constant;
import org.jarvis.code.util.RestApiFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KimChheng on 6/4/2017.
 */

public class WeddingFragment extends Fragment implements Callback<ResponseEntity<Product>>, WeddingAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private WeddingAdapter adapter;
    private List<Product> products;
    private WeddingApi weddingApi;
    private TextView txtMessage;
    private static final int LIMIT = 5;
    private int offset = 1;

    public WeddingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        adapter = new WeddingAdapter(getContext(), products);
        weddingApi = RestApiFactory.build(WeddingApi.class);
        weddingApi.fetchProducts(1, LIMIT).enqueue(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wedding_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewWedding);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(this);
        if (products.isEmpty()) {
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            ResponseEntity<Product> responseEntity = response.body();
            Log.i(Constant.TAG, responseEntity.getData().toString());
            adapter.addAll(responseEntity.getData());
            progressBar.setVisibility(View.GONE);
            offset = 2;
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Log.e(Constant.TAG, t.getMessage());
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        txtMessage.setText(t.getMessage());
        txtMessage.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore() {
        Log.d("WeddingFragment", "onLoadMore");
        products.add(null);
        adapter.notifyItemInserted(products.size() - 1);
        if (offset == 1) offset++;
        weddingApi.fetchProducts(offset, LIMIT).enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if (response.code() == 200) {
                    products.remove(products.size() - 1);
                    adapter.notifyItemRemoved(products.size());
                    List<Product> list = response.body().getData();
                    if (!list.isEmpty()) {
                        for (Product product : list)
                            adapter.add(product);
                        offset++;
                        adapter.setLoaded();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Log.e(Constant.TAG, t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                products.remove(products.size() - 1);
                adapter.notifyItemRemoved(products.size());
            }
        });

    }

    @Override
    public void onRefresh() {
        Log.d("WeddingFragment", "onRefresh");
        weddingApi.fetchProducts(1, LIMIT).enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if (response.code() == 200) {
                    products.clear();
                    adapter.addAll(response.body().getData());
                    progressBar.setVisibility(View.GONE);
                    txtMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.setLoaded();
                    offset = 1;
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
                Log.e(Constant.TAG, t.getMessage());
                progressBar.setVisibility(View.GONE);
                txtMessage.setText(t.getMessage());
                txtMessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
