package org.jarvis.code.model;


import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KimChheng on 6/16/2017.
 */

public class Customer extends BaseRequest {

    @SerializedName("GROOM_NAME")
    private String groomName;
    @SerializedName("GROOM_DAD_NAME")
    private String groomDadName;
    @SerializedName("GROOM_MOM_NAME")
    private String groomMomName;
    @SerializedName("BRIDE_NAME")
    private String brideName;
    @SerializedName("BRIDE_DAD_NAME")
    private String brideDadName;
    @SerializedName("BRIDE_MOM_NAME")
    private String brideMomName;
    @SerializedName("HOME")
    private String home;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("DATE")
    private String date;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("PHONE")
    private String phone;
    @SerializedName("FACEBOOK")
    private String fb;
    @SerializedName("MAP")
    private String map;
    @SerializedName("OTHER")
    private String other;
    @SerializedName("PRODUCT_ID")
    private int productId;
    @SerializedName("COLOR")
    private String color;

    public Customer() {
        super();
    }

    public Customer(Parcel source) {
        groomName = source.readString();
        groomDadName = source.readString();
        groomMomName = source.readString();
        brideName = source.readString();
        brideDadName = source.readString();
        brideMomName = source.readString();
        home = source.readString();
        address = source.readString();
        date = source.readString();
        email = source.readString();
        phone = source.readString();
        fb = source.readString();
        map = source.readString();
        other = source.readString();
        productId = source.readInt();
        color=source.readString();
    }

    public String getGroomName() {
        return groomName;
    }

    public void setGroomName(String groomName) {
        this.groomName = groomName;
    }

    public String getGroomDadName() {
        return groomDadName;
    }

    public void setGroomDadName(String groomDadName) {
        this.groomDadName = groomDadName;
    }

    public String getGroomMomName() {
        return groomMomName;
    }

    public void setGroomMomName(String groomMomName) {
        this.groomMomName = groomMomName;
    }

    public String getBrideName() {
        return brideName;
    }

    public void setBrideName(String brideName) {
        this.brideName = brideName;
    }

    public String getBrideDadName() {
        return brideDadName;
    }

    public void setBrideDadName(String brideDadName) {
        this.brideDadName = brideDadName;
    }

    public String getBrideMomName() {
        return brideMomName;
    }

    public void setBrideMomName(String brideMomName) {
        this.brideMomName = brideMomName;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel parcel) {
            return new Customer(parcel);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groomName);
        dest.writeString(groomDadName);
        dest.writeString(groomMomName);
        dest.writeString(brideName);
        dest.writeString(brideDadName);
        dest.writeString(brideMomName);
        dest.writeString(home);
        dest.writeString(address);
        dest.writeString(date);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(fb);
        dest.writeString(map);
        dest.writeString(other);
        dest.writeInt(productId);
        dest.writeString(color);
    }
}
