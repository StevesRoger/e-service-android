package org.jarvis.code.core.control;

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
import org.jarvis.code.core.adapter.ImageAdapter;
import org.jarvis.code.core.model.read.Product;

/**
 * Created by KimChheng on 6/19/2017.
 */

public class DialogView extends DialogFragment {

    private Product product;

    public DialogView() {

    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
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
        ImageViewTouchViewPager viewPager = (ImageViewTouchViewPager) view.findViewById(R.id.imagePager);
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
