package org.jarvis.code.model.read;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ki.kao on 9/2/2017.
 */

public class Advertisement extends BaseResponse {

    @SerializedName("IMAGE")
    private Integer image;


    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
