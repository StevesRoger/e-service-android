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
import org.jarvis.code.api.RequestService;
import org.jarvis.code.core.adapter.ProductAdapter;
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

public class DesignFragment extends Fragment implements Callback<ResponseEntity<Product>>, ProductAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private ProductAdapter adapter;
    private List<Product> products;
    private RequestService requestService;
    private TextView lblMessage;
    private static final int LIMIT = 3;
    private int offset = 1;

    public DesignFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), products);
        adapter.setOnLoadMoreListener(this);
        requestService = RestApiFactory.build(RequestService.class);
        requestService.fetchProducts(1, LIMIT, "DES").enqueue(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!products.isEmpty())
            progressBar.setVisibility(View.GONE);
        adapter.setRecyclerView(recyclerView);
        Log.i(Constant.TAG, "DesignFragment.onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desgin_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        lblMessage = (TextView) view.findViewById(R.id.txtMessage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewWedding);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRefresh() {
        requestService.fetchProducts(1, LIMIT, "DES").enqueue(new Callback<ResponseEntity<Product>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
                if (response.code() == 200) {
                    products.clear();
                    adapter.addAll(response.body().getData());
                    progressBar.setVisibility(View.GONE);
                    lblMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.setLoaded();
                    offset = 1;
                    if (products.isEmpty()) {
                        lblMessage.setText("There is no product from server!");
                        lblMessage.setVisibility(View.VISIBLE);
                    }
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
        });
    }

    @Override
    public void onLoadMore() {
        if (products.size() > 2) {
            if (offset == 1) offset++;
            products.add(null);
            adapter.notifyItemInserted(products.size() - 1);
            requestService.fetchProducts(offset, LIMIT, "DES").enqueue(new Callback<ResponseEntity<Product>>() {
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
    }

    @Override
    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            ResponseEntity<Product> responseEntity = response.body();
            Log.i(Constant.TAG, responseEntity.getData().toString());
            adapter.addAll(responseEntity.getData());
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            offset = 2;
            if (products.isEmpty()) {
                lblMessage.setText("There is no product from server!");
                lblMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Log.e(Constant.TAG, t.getMessage());
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
