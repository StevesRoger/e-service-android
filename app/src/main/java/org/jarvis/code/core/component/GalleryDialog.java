package org.jarvis.code.core.component;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jarvis.code.R;
import org.jarvis.code.core.adapter.ImageAdapter;
import org.jarvis.code.core.model.response.Product;

/**
 * Created by KimChheng on 6/19/2017.
 */

public class GalleryDialog extends DialogFragment {

    private Product product;

    public GalleryDialog() {

    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_view, container, false);
        ImageViewTouchViewPager viewPager = (ImageViewTouchViewPager) view.findViewById(R.id.imagePager);
        ImageAdapter adapter = new ImageAdapter(getContext(), this, product);
        viewPager.setAdapter(adapter);
        return view;
    }



   /* @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_view, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.imagePager);
        ImageAdapter adapter = new ImageAdapter(getContext());
        viewPager.setAdapter(adapter);
        builder.setView(view);
        return builder.create();
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = getActivity().getLayoutInflater().inflate(R.layout.popup_view, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.imagePager);
        ImageAdapter adapter = new ImageAdapter(getContext());
        viewPager.setAdapter(adapter);
        dialog.setContentView(view);
        return dialog;
    }*/
}
