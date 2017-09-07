package org.jarvis.code.core.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.core.component.DialogView;
import org.jarvis.code.core.fragment.RegisterFragment;
import org.jarvis.code.core.model.read.Product;
import org.jarvis.code.core.model.read.Promotion;
import org.jarvis.code.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimChheng on 6/2/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    private final int VIEW_PRODUCT = 0;
    private final int VIEW_PROMOTION = 1;
    private final int VIEW_LOADING = 2;

    private List<Product> originalList;
    private List<Product> copyList;
    private Context context;

    private static String imgUrl = Constant.BASE_URL + "mobile/image/view/";

    public ProductAdapter(Context context, List<Product> products) {
        this.originalList = products;
        this.context = context;
        this.copyList = new ArrayList<>();
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
            Product product = originalList.get(position);
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.product = product;
            productViewHolder.lblCode.setText(context.getResources().getString(R.string.string_code) + product.getCode());
            productViewHolder.lblColor.setText(context.getResources().getString(R.string.string_color) + product.getColor());
            productViewHolder.lblSize.setText(context.getResources().getString(R.string.string_size) + product.getSize());
            productViewHolder.lblPrice.setText(context.getResources().getString(R.string.string_price) + product.getPrice());
            productViewHolder.lblContact.setText(context.getResources().getString(R.string.string_contact) + product.getContact().getPhone1());
            Picasso.with(context).load(imgUrl + product.getImages().get(0))
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(productViewHolder.image);
        } else if (holder instanceof PromotionViewHolder) {
            Promotion promotion = (Promotion) originalList.get(position);
            PromotionViewHolder promotionViewHolder = (PromotionViewHolder) holder;
            Picasso.with(context).load(imgUrl + promotion.getImages().get(0))
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(promotionViewHolder.image);
        } else {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return originalList == null ? 0 : originalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Product item = originalList.get(position);
        if (item != null) {
            if (item instanceof Promotion)
                return VIEW_PROMOTION;
            else
                return VIEW_PRODUCT;
        } else
            return VIEW_LOADING;
    }

    public void addAll(List<Product> products) {
        originalList.addAll(products);
        copyList.addAll(products);
    }

    public void add(Product product) {
        originalList.add(product);
        copyList.add(product);
    }

    public void add(int index, Product product) {
        originalList.add(index, product);
        copyList.add(index, product);
    }

    public void remove(int index) {
        originalList.remove(index);
        copyList.remove(index);
    }

    public void clear() {
        originalList.clear();
        copyList.clear();
    }

    public List<Product> getOriginalList() {
        return originalList;
    }

    public List<Product> getCopyList() {
        return copyList;
    }

    public void filter(String text) {
        originalList.clear();
        if (text.isEmpty()) {
            originalList.addAll(copyList);
        } else {
            text = text.toLowerCase();
            for (Product product : copyList) {
                if (!(product instanceof Promotion)) {
                    if (product.getCode().toLowerCase().contains(text) ||
                            product.getColor().toLowerCase().contains(text) ||
                            product.getPrice().toLowerCase().contains(text) ||
                            product.getSize().toLowerCase().contains(text)) {
                        originalList.add(product);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblCode;
        public TextView lblPrice;
        public TextView lblSize;
        public TextView lblColor;
        public TextView lblContact;
        public AppCompatButton btnRegister;
        public ImageView image;
        public Product product;

        public ProductViewHolder(View itemView) {
            super(itemView);
            lblCode = (TextView) itemView.findViewById(R.id.lblCode);
            lblPrice = (TextView) itemView.findViewById(R.id.lblPrice);
            lblSize = (TextView) itemView.findViewById(R.id.lblSize);
            lblColor = (TextView) itemView.findViewById(R.id.lblColor);
            lblContact = (TextView) itemView.findViewById(R.id.lblContact);
            btnRegister = (AppCompatButton) itemView.findViewById(R.id.btnRegister);
            image = (ImageView) itemView.findViewById(R.id.imgViewProduct);
            btnRegister.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            if (view.getId() == R.id.btnRegister) {
                Bundle bundles = new Bundle();
                bundles.putSerializable("product", product);
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(bundles);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, registerFragment)
                        .addToBackStack("registerFragment")
                        .commit();
            } else {
                DialogView dialogView = new DialogView();
                dialogView.setProduct(product);
                dialogView.show(fragmentManager.beginTransaction(), "Image Gallery");
                //dialogView.setCancelable(false);
            }
        }
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public PromotionViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imgViewPromotion);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }


}
