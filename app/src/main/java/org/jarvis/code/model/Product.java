package org.jarvis.code.model;

import android.content.Context;
import android.os.Parcel;
import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimChheng on 5/29/2017.
 */

public class Product extends BaseResponse implements ListAdapter.ListAdapterItem {

    private static final long serialVersionUID = 1L;
    @SerializedName("CODE")
    private String code;
    @SerializedName("SIZE")
    private String size;
    @SerializedName("PRICE")
    private String price;
    @SerializedName("COLOR")
    private String color;
    @SerializedName("CONTACT")
    private Contact contact;
    @SerializedName("IMAGES")
    private List<Integer> images;
    @SerializedName("TYPE")
    private String type;

    public Product() {
        super();
    }

    public Product(Parcel source) {
        id = source.readInt();
        code = source.readString();
        size = source.readString();
        price = source.readString();
        color = source.readString();
        contact = source.readParcelable(Contact.class.getClassLoader());
        images = (ArrayList<Integer>) source.readSerializable();
        type = source.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(size);
        dest.writeString(price);
        dest.writeString(color);
        dest.writeParcelable(contact, flags);
        dest.writeList(images);
        dest.writeString(type);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Contact getContact() {
        return contact != null ? contact : new Contact();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                ", contact=" + contact +
                ", images=" + images +
                ", type='" + type + '\'' +
                '}';
    }

    public String[] getColors() {
        if (color != null && !color.isEmpty() && color.contains("#"))
            return color.split(",");
        return new String[0];
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Product product = new Product();
        product.setId(this.id);
        product.setCode(this.code);
        product.setSize(this.size);
        product.setPrice(this.price);
        product.setColor(this.color);
        product.setContact(this.contact);
        product.setImages(this.images);
        return product;
    }

    public void update(Product product) {
        if (!product.getId().equals(this.id))
            this.setId(product.getId());
        if (!product.getCode().equals(this.code))
            this.setCode(product.getCode());
        if (!product.getSize().equals(this.size))
            this.setSize(product.getSize());
        if (!product.getPrice().equals(this.price))
            this.setPrice(product.getPrice());
        if (!product.getColor().equals(this.color))
            this.setColor(product.getColor());
        if (!product.getContact().equals(this.contact))
            this.setContact(product.getContact());
        if (product.getImages() != null && !product.getImages().isEmpty())
            this.setImages(product.getImages());
    }

    @Override
    public void viewImage(Context context, ImageView imageView) {
        int index = 1;
        if (images != null && !images.isEmpty())
            index = images.get(0);
        Picasso.with(context).load(imgUrl + index).fit().centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.no_image_available)
                .into(imageView);
    }
}
