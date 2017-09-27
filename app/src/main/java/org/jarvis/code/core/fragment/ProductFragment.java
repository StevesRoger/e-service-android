package org.jarvis.code.core.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.activity.MainActivity;
import org.jarvis.code.api.RequestClient;
import org.jarvis.code.core.adapter.LoadMoreHandler;
import org.jarvis.code.core.adapter.ProductAdapter;
import org.jarvis.code.core.model.read.Product;
import org.jarvis.code.core.model.read.Promotion;
import org.jarvis.code.core.model.read.ResponseEntity;
import org.jarvis.code.util.Loggy;
import org.jarvis.code.util.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 9/1/2017.
 */

public class ProductFragment extends Fragment implements IFragment<Product> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private RequestClient requestService;
    private TextView lblMessage;

    private ProductAdapter adapter;
    private List<Product> products;
    private LoadMoreHandler<Product> loadMoreHandler;
    private MainActivity mainActivity;

    private String type;
    private final int LIMIT = 5;
    private int offset = 1;
    private int position = 5;
    private int page = 1;

    public static ProductFragment newInstance(String type, MainActivity activity) {
        ProductFragment fragment = new ProductFragment();
        fragment.type = type;
        fragment.mainActivity = activity;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), products);
        requestService = RequestFactory.build(RequestClient.class);
        requestService.fetchProducts(1, LIMIT, type).enqueue(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        lblMessage = (TextView) view.findViewById(R.id.txtMessage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!products.isEmpty())
            progressBar.setVisibility(View.GONE);
        recyclerView.addOnScrollListener(loadMoreHandler = new LoadMoreHandler(this, recyclerView));
    }

    @Override
    public void search(String text) {
        adapter.filter(text);
    }

    @Override
    public void onLoadMore() {
        Loggy.i(ProductFragment.class, "Load more product");
        adapter.add(null);
        recyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyItemInserted(products.size() - 1);
                requestService.fetchProducts(offset, LIMIT, type).enqueue(loadMoreHandler);
            }
        });
    }

    @Override
    public void onLoadMoreSuccess(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            adapter.remove(products.size() - 1);
            adapter.notifyItemRemoved(products.size());
            List<Product> list = response.body().getData();
            if (!list.isEmpty()) {
                for (Product product : list)
                    adapter.add(product);
                fetchPromotion(page);
                adapter.notifyDataSetChanged();
                offset++;
                loadMoreHandler.loaded();
            }
        }
    }

    @Override
    public void onLoadMoreFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Loggy.e(ProductFragment.class, t.getMessage());
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        adapter.remove(products.size() - 1);
        adapter.notifyItemRemoved(products.size());
    }

    @Override
    public void onRefresh() {
        mainActivity.onRefreshAD();
        requestService.fetchProducts(1, LIMIT, type).enqueue(this);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Call<ResponseEntity<Product>> call, Response<ResponseEntity<Product>> response) {
        if (response.code() == 200) {
            ResponseEntity<Product> responseEntity = response.body();
            Loggy.i(ProductFragment.class, responseEntity.getData().toString());
            loadMoreHandler.loaded();
            adapter.clear();
            adapter.addAll(responseEntity.getData());
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            offset = 2;
            position = 5;
            page = 1;
            if (products.size() == 5)
                fetchPromotion(page);
            if (products.isEmpty() && lblMessage.getVisibility() == View.GONE) {
                lblMessage.setText("There is no product from server!");
                lblMessage.setVisibility(View.VISIBLE);
            } else
                lblMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Product>> call, Throwable t) {
        Loggy.e(ProductFragment.class, t.getMessage());
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void fetchPromotion(final int step) {
        Loggy.i(ProductFragment.class, "advertisement offset:" + step);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Response<ResponseEntity<Promotion>> response = requestService.fetchPromotions(step, 1).execute();
                    if (response.code() == 200) {
                        ResponseEntity<Promotion> body = response.body();
                        if (body != null && !body.getData().isEmpty()) {
                            adapter.add(position, body.getData().get(0));
                            Loggy.i(ProductFragment.class, "advertisement position:" + position);
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
    }

}
