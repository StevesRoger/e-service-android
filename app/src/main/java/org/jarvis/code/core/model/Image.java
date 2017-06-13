package org.jarvis.code.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KimChheng on 5/30/2017.
 */

public class Image extends AbstractEntity {

    @SerializedName("NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
