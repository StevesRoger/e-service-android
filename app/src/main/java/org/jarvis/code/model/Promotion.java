package org.jarvis.code.model;

import android.content.Context;
import android.os.Parcel;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ListAdapter;
import org.jarvis.code.util.Animator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ki.kao on 8/26/2017.
 */

public class Promotion extends BaseResponse implements ListAdapter.ListAdapterItem {

    @SerializedName("DESC")
    private String link;
    @SerializedName("IMAGES")
    protected List<Integer> images;

    public Promotion() {
        super();
    }

    public Promotion(Parcel source) {
        super();
        id = source.readInt();
        images = (ArrayList<Integer>) source.readSerializable();
        link = source.readString();
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeList(images);
        dest.writeString(link);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "link='" + link + '\'' +
                '}';
    }

    @Override
    public void viewImage(Context context, ImageView imageView) {
        if (images.size() > 1) {
            new Animator(imageView, images, context).animatePromotion(0, true);
        } else {
            Picasso.with(context).load(imgUrl + images.get(0)).fit().centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image_available)
                    .into(imageView);
        }
    }
}
