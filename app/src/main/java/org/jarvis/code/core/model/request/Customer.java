package org.jarvis.code.core.model.request;

import com.google.gson.annotations.SerializedName;

import org.jarvis.code.core.model.request.base.AbstractRequest;

/**
 * Created by KimChheng on 6/16/2017.
 */

public class Customer extends AbstractRequest{
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
    @SerializedName("PHONE")
    private String phone;
    @SerializedName("OTHER")
    private String other;
    @SerializedName("PRODUCT_ID")
    private int productId;

    public Customer() {
    }

    public Customer(String groomName, String groomDadName, String groomMomName, String brideName, String brideDadName, String brideMomName, String home, String address, String date, String phone, String other, int productId) {
        this.groomName = groomName;
        this.groomDadName = groomDadName;
        this.groomMomName = groomMomName;
        this.brideName = brideName;
        this.brideDadName = brideDadName;
        this.brideMomName = brideMomName;
        this.home = home;
        this.address = address;
        this.date = date;
        this.phone = phone;
        this.other = other;
        this.productId = productId;
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
}
