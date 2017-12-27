package org.jarvis.code.adapter.viewholder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jarvis.code.R;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.customer.RegisterFragment;
import org.jarvis.code.ui.customer.forms.HomPartyForm;
import org.jarvis.code.ui.customer.forms.WeddingForm;
import org.jarvis.code.ui.widget.ColorView;
import org.jarvis.code.ui.widget.DialogView;

import butterknife.BindView;

/**
 * Created by ki.kao on 12/4/2017.
 */

public class ProductViewHolder extends BaseViewHolder<Product> implements View.OnClickListener {

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
            switch (item.getType()) {
                case "WED":
                case "DES":
                    startForm(fragmentManager, "wedding", WeddingForm.newInstance(item));
                    break;
                case "CER":
                    break;
                case "HOM":
                    startForm(fragmentManager, "home party", HomPartyForm.newInstance(item));
                    break;
                case "HBD":
                    break;
                case "INV":
                    break;
            }

        } else {
            DialogView dialogView = DialogView.newInstance(item);
            dialogView.show(fragmentManager.beginTransaction(), "Image Gallery");
            //dialogView.setCancelable(false);
        }
    }

    @Override
    public void setItem(Product item) {
        super.setItem(item);
        Product product = (Product) this.item;
        this.lblCode.setText(context.getResources().getString(R.string.code) + product.getCode());
        this.colorView.setColor(product.getColors());
        this.lblSize.setText(context.getResources().getString(R.string.size) + product.getSize());
        this.lblPrice.setText(context.getResources().getString(R.string.price_between) + product.getPrice());
        this.lblContact.setText(context.getResources().getString(R.string.contact) + product.getContact().getPhone1());
    }

    public ImageView getImageView() {
        return image;
    }

    private void startForm(FragmentManager fragmentManager, String tag, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(tag)
                .commit();
    }
}
