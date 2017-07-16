package org.jarvis.code.core.model.response;

import com.google.gson.annotations.SerializedName;

import org.jarvis.code.core.model.response.base.AbstractResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KimChheng on 5/29/2017.
 */

public class Product extends AbstractResponse {

    private static final long serialVersionUID = 1L;
    @SerializedName("CODE")
    private String code;
    @SerializedName("SIZE")
    private Integer size;
    @SerializedName("PRICE")
    private String price;
    @SerializedName("COLOR")
    private String color;
    @SerializedName("CONTACT")
    private Contact contact;
    @SerializedName("IMAGES")
    private List<Image> images;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
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
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public static class Image implements Serializable{

        @SerializedName("IMG_ID")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
