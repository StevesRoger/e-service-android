package org.jarvis.code.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.adapter.viewholder.LoadingViewHolder;
import org.jarvis.code.adapter.viewholder.ProductViewHolder;
import org.jarvis.code.adapter.viewholder.PromotionViewHolder;
import org.jarvis.code.model.Product;
import org.jarvis.code.model.Promotion;
import org.jarvis.code.util.Animator;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import java.util.List;

/**
 * Created by KimChheng on 6/2/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    private final int VIEW_PRODUCT = 0;
    private final int VIEW_PROMOTION = 1;
    private final int VIEW_LOADING = 2;

    private List<Product> data;
    private ArrayMap<Integer, Product> map;
    private Context context;

    private static String imgUrl = Constants.BASE_URL + "mobile/image/view/";

    public ProductAdapter(Context context, List<Product> products) {
        this.data = products;
        this.context = context;
        this.map = new ArrayMap();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_PRODUCT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        } else if (viewType == VIEW_PROMOTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
            return new PromotionViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            Product product = data.get(position);
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.setContext(context);
            productViewHolder.render(product);
            Picasso.with(context).load(imgUrl + product.getImages().get(0)).fit().centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(productViewHolder.getImage());
        } else if (holder instanceof PromotionViewHolder) {
            Promotion promotion = (Promotion) data.get(position);
            PromotionViewHolder promotionViewHolder = (PromotionViewHolder) holder;
            promotionViewHolder.setContext(context);
            promotionViewHolder.setData(promotion);
            new Animator(promotionViewHolder.getImage(), promotion.getImages(), context).animatePromotion(0, true);
        } else {
            ((LoadingViewHolder) holder).getProgressBar().setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        Product item = data.get(position);
        if (item != null) {
            if (item instanceof Promotion)
                return VIEW_PROMOTION;
            else
                return VIEW_PRODUCT;
        } else
            return VIEW_LOADING;
    }

    public void addAll(List<Product> products) {
        data.addAll(products);
        putAll(products);
    }

    public void add(Product product) {
        data.add(product);
        put(product);
    }

    public void add(int index, Product product) {
        data.add(index, product);
        put(product);
    }

    public void put(int key, Product value) {
        data.add(value);
        map.put(key, value);
    }

    private void put(Product product) {
        if (product != null && product.getId() != null)
            map.put(product.getId(), product);
    }

    private void putAll(List<Product> products) {
        for (Product product : products) {
            if (product != null)
                map.put(product.getId(), product);
        }
    }

    public void remove(int index) {
        Product product = data.remove(index);
        if (product != null)
            removeByKey(product.getId());
    }

    public void removeByKey(int key) {
        Product product = map.remove(key);
        if (product != null)
            remove(product.getId());
    }

    public void clear() {
        data.clear();
        map.clear();
    }

    public void updateItems(List<Product> products) {
        for (Product product : products)
            updateItem(product);
    }

    public void updateItem(Product product) {
        if (product != null) {
            Product tmp = map.get(product.getId());
            if (tmp != null)
                tmp.update(product);
            else if (product.getId() == null || !map.containsKey(product.getId()))
                add(product);
        }
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void filter(String text) {
        Loggy.i(ProductAdapter.class, "Search product '" + text + "'");
        data.clear();
        if (!text.isEmpty()) {
            for (Product product : map.values()) {
                if (product instanceof Product) {
                    if (product.getCode().toLowerCase().contains(text) ||
                            product.getColor().toLowerCase().contains(text) ||
                            product.getPrice().toLowerCase().contains(text) ||
                            product.getSize().toLowerCase().contains(text)) {
                        data.add(product);
                    }
                }
            }
        } else {
            data.addAll(map.values());
        }
        notifyDataSetChanged();
    }
}
