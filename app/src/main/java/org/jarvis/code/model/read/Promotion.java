package org.jarvis.code.model.read;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ki.kao on 8/26/2017.
 */

public class Promotion extends Product {

    @SerializedName("DESC")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
