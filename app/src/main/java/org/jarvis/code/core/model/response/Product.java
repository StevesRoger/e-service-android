package org.jarvis.code.core.model.response;

import com.google.gson.annotations.SerializedName;

import org.jarvis.code.core.model.response.base.AbstractResponse;

import java.util.List;

/**
 * Created by KimChheng on 5/29/2017.
 */

public class Product extends AbstractResponse {

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
    private transient Promotion promotion;

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

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
