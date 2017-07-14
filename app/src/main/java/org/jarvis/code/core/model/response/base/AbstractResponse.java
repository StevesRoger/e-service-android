package org.jarvis.code.core.model.response.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KimChheng on 5/29/2017.
 */

public abstract class AbstractResponse implements Serializable{

    @SerializedName("ID")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
