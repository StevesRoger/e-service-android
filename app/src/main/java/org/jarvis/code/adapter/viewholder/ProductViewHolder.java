package org.jarvis.code.adapter.viewholder;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jarvis.code.R;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.register.RegisterFragment;
import org.jarvis.code.ui.widget.ColorView;
import org.jarvis.code.ui.widget.DialogView;

import butterknife.BindView;

/**
 * Created by ki.kao on 12/4/2017.
 */

public class ProductViewHolder extends BaseViewHolder implements View.OnClickListener {

    @BindView(R.id.lblCode)
    TextView lblCode;
    @BindView(R.id.lblPrice)
    TextView lblPrice;
    @BindView(R.id.lblSize)
    TextView lblSize;
    @BindView(R.id.lblContact)
    TextView lblContact;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;
    @BindView(R.id.img_view_product)
    ImageView image;
    @BindView(R.id.colorView)
    ColorView colorView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        btnRegister.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        if (view.getId() == R.id.btn_register) {
            RegisterFragment registerFragment = RegisterFragment.newInstance((Product) object);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, registerFragment)
                    .addToBackStack("registerFragment")
                    .commit();
        } else {
            DialogView dialogView = DialogView.newInstance((Product) object);
            dialogView.show(fragmentManager.beginTransaction(), "Image Gallery");
            //dialogView.setCancelable(false);
        }
    }

    public void render(Product product) {
        setData(product);
        this.lblCode.setText(context.getResources().getString(R.string.string_code) + product.getCode());
        this.colorView.setColor(product.getColors());
        this.lblSize.setText(context.getResources().getString(R.string.string_size) + product.getSize());
        this.lblPrice.setText(context.getResources().getString(R.string.string_price) + product.getPrice());
        this.lblContact.setText(context.getResources().getString(R.string.string_contact) + product.getContact().getPhone1());
    }

    public ImageView getImage() {
        return image;
    }
}
