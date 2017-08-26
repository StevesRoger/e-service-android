package org.jarvis.code.core.model.response;

import com.google.gson.annotations.SerializedName;

import org.jarvis.code.core.model.response.base.AbstractResponse;

import java.util.List;

/**
 * Created by ki.kao on 8/26/2017.
 */

public class Promotion extends AbstractResponse {

    @SerializedName("CODE")
    private String code;
    @SerializedName("DESC")
    private String desc;
    @SerializedName("IMAGES")
    private List<Integer> images;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
