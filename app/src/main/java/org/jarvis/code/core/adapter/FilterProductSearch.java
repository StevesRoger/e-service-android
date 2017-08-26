package org.jarvis.code.core.adapter;

import android.widget.Filter;

import org.jarvis.code.core.model.response.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ki.kao on 8/26/2017.
 */

public class FilterProductSearch extends Filter {

    private ProductAdapter adapter;
    private List<Product> list;
    private List<Product> filteredList;

    public FilterProductSearch(ProductAdapter adapter, List<Product> list) {
        this.adapter = adapter;
        this.list = list;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() == 0) {
            filteredList.addAll(list);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (Product product : list) {
                if (product.getCode().toLowerCase().contains(filterPattern) ||
                        product.getColor().toLowerCase().contains(filterPattern) ||
                        product.getPrice().toLowerCase().contains(filterPattern) ||
                        product.getSize().toLowerCase().contains(filterPattern)) {
                    filteredList.add(product);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.getOriginalList().clear();
        adapter.getOriginalList().addAll((ArrayList<Product>) results.values);
        adapter.notifyDataSetChanged();
    }

    public interface FilterProduct {
        void search(String text);
    }
}
