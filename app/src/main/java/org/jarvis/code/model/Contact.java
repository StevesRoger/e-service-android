package org.jarvis.code.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KimChheng on 5/30/2017.
 */

public class Contact extends BaseResponse {

    @SerializedName("PHONE1")
    private String phone1 = "";
    @SerializedName("PHONE2")
    private String phone2 = "";
    @SerializedName("EMAIL")
    private String email = "";
    @SerializedName("FACEBOOK")
    private String facebook = "";

    public Contact() {
        super();
    }

    public Contact(Parcel source) {
        phone1 = source.readString();
        phone2 = source.readString();
        email = source.readString();
        facebook = source.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone1);
        dest.writeString(phone2);
        dest.writeString(email);
        dest.writeString(facebook);
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

}
