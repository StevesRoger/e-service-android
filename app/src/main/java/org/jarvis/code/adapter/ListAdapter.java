package org.jarvis.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jarvis.code.R;
import org.jarvis.code.adapter.viewholder.LoadingViewHolder;
import org.jarvis.code.adapter.viewholder.ProductViewHolder;
import org.jarvis.code.adapter.viewholder.PromotionViewHolder;
import org.jarvis.code.model.Product;
import org.jarvis.code.model.Promotion;
import org.jarvis.code.util.Loggy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimChheng on 6/2/2017.
 */

public class ListAdapter extends RecyclerView.Adapter {

    private final int VIEW_PRODUCT = 0;
    private final int VIEW_PROMOTION = 1;
    private final int VIEW_LOADING = 2;

    private List<ListAdapterItem> list;
    private List<ListAdapterItem> listCopy;
    private Context context;

    public ListAdapter(Context context) {
        this.list = new ArrayList();
        this.listCopy = new ArrayList();
        this.context = context;
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
        ListAdapterItem item = null;
        if (holder instanceof ProductViewHolder) {
            item = list.get(position);
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.setContext(context);
            productViewHolder.setItem(item);
            item.viewImage(context, productViewHolder.getImageView());
        } else if (holder instanceof PromotionViewHolder) {
            item = list.get(position);
            PromotionViewHolder promotionViewHolder = (PromotionViewHolder) holder;
            promotionViewHolder.setContext(context);
            promotionViewHolder.setItem(item);
            item.viewImage(context, promotionViewHolder.getImageView());
        } else {
            ((LoadingViewHolder) holder).getProgressBar().setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        ListAdapterItem item = list.get(position);
        if (item != null) {
            if (item instanceof Promotion)
                return VIEW_PROMOTION;
            else
                return VIEW_PRODUCT;
        } else
            return VIEW_LOADING;
    }

    public boolean addAll(List items) {
        list.addAll(items);
        return listCopy.addAll(items);
    }

    public boolean add(ListAdapterItem item) {
        list.add(item);
        return listCopy.add(item);
    }

    public void add(int index, ListAdapterItem item) {
        list.add(index, item);
        listCopy.add(index, item);
    }

    public ListAdapterItem remove(int index) {
        list.remove(index);
        return listCopy.remove(index);
    }

    public void clear() {
        list.clear();
        listCopy.clear();
    }

    public void updateItems(List<Product> products) {
        for (Product product : products)
            updateItem(product);
    }

    public void updateItem(Product product) {
        /*if (product != null) {
            Product tmp = map.get(product.getId());
            if (tmp != null)
                tmp.update(product);
            else if (product.getId() == null || !map.containsKey(product.getId()))
                add(product);
        }*/
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void search(String text) {
        Loggy.i(ListAdapter.class, "Search product '" + text + "'");
        list.clear();
        if (!text.isEmpty()) {
            for (ListAdapterItem item : listCopy) {
                if (item instanceof Product) {
                    Product product = (Product) item;
                    if (product.getCode().toLowerCase().contains(text) ||
                            product.getColor().toLowerCase().contains(text) ||
                            product.getPrice().toLowerCase().contains(text) ||
                            product.getSize().toLowerCase().contains(text)) {
                        list.add(product);
                    }
                }
            }
        } else {
            list.addAll(listCopy);
        }
        notifyDataSetChanged();
    }

    public interface ListAdapterItem {

        void viewImage(Context context, ImageView imageView);
    }
}
