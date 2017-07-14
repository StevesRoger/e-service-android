package org.jarvis.code.core.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jarvis.code.core.model.response.base.AbstractResponse;

import java.io.Serializable;

/**
 * Created by KimChheng on 5/30/2017.
 */

public class Contact implements Serializable{

    @SerializedName("PHONE1")
    private String phone1;
    @SerializedName("PHONE2")
    private String phone2;
    @SerializedName("PHONE3")
    private String phone3;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("FACEBOOK")
    private String facebook;

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

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
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
