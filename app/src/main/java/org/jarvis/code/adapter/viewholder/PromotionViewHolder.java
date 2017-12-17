package org.jarvis.code.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import org.jarvis.code.R;
import org.jarvis.code.model.Promotion;

import butterknife.BindView;

/**
 * Created by ki.kao on 12/4/2017.
 */

public class PromotionViewHolder extends BaseViewHolder implements View.OnClickListener {

    @BindView(R.id.img_view_promotion)
    ImageView image;

    public PromotionViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public ImageView getImageView() {
        return image;
    }

    @Override
    public void onClick(View v) {
        Promotion promotion = (Promotion) item;
        String url = "http://www.google.com";
        if (promotion != null && promotion.getLink() != null && !promotion.getLink().isEmpty()) {
            url = promotion.getLink();
            if (!url.startsWith("http") || !url.startsWith("https"))
                url = "http://" + url;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }
}