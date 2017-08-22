package org.jarvis.code.core.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.core.fragment.RegisterFragment;
import org.jarvis.code.core.model.response.Product;
import org.jarvis.code.util.Constant;

import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by KimChheng on 6/19/2017.
 */

public class ImageAdapter extends PagerAdapter implements View.OnClickListener {

    private static String imgUrl = Constant.BASE_URL + "mobile/image/view/";
    private Product product;
    private Context context;
    private LayoutInflater layoutInflater;
    private DialogFragment dialogFragment;
    List<Integer> images;
    private boolean isLandscape = false;

    public ImageAdapter(Context context, DialogFragment dialogFragment, Product product, boolean isLandscape) {
        this.context = context;
        this.dialogFragment = dialogFragment;
        this.product = product;
        this.images = product.getImages();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isLandscape = isLandscape;
    }

    @Override
    public int getCount() {
        return images != null ? images.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.image_view, container, false);

        TextView register = (TextView) view.findViewById(R.id.registerText);
        register.setOnClickListener(this);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ImageView imageBack = (ImageView) view.findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);

        ImageViewTouch imageView = (ImageViewTouch) view.findViewById(R.id.imageView);
        if (isLandscape) {
            ViewGroup.LayoutParams layoutParams = null;

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.imageLayout);
            layoutParams = linearLayout.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

            layoutParams = imageView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        Picasso.with(context).load(imgUrl + images.get(position))
                .fit()
                .centerCrop()
                .placeholder(R.drawable.progress_spinning)
                .error(R.drawable.img_not_available)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
        switch (view.getId()) {
            case R.id.registerText:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialogFragment != null && dialogFragment.isVisible())
                            dialogFragment.dismiss();
                    }
                }, 500);
                Bundle bundles = new Bundle();
                bundles.putSerializable("product", product);
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(bundles);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, registerFragment)
                        .addToBackStack("registerFragment")
                        .commit();
                break;
            case R.id.imageBack:
                if (dialogFragment != null && dialogFragment.isVisible())
                    dialogFragment.dismiss();
                break;
            default:
                break;
        }
    }
}
