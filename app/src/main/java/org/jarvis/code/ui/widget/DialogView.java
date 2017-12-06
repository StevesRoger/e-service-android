package org.jarvis.code.ui.widget;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ImageAdapter;
import org.jarvis.code.model.Product;

/**
 * Created by KimChheng on 6/19/2017.
 */

public class DialogView extends DialogFragment {

    private Product product;

    public static DialogView newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        DialogView dialogView = new DialogView();
        dialogView.setArguments(bundle);
        return dialogView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view, container, false);
        ImageViewTouchViewPager viewPager = (ImageViewTouchViewPager) view.findViewById(R.id.image_view_touch);
        boolean isLandscape = false;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "Landscape Mode", Toast.LENGTH_LONG).show();
            isLandscape = true;

        }
        ImageAdapter adapter = new ImageAdapter(getContext(), this, product, isLandscape);
        viewPager.setAdapter(adapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}
