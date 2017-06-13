package org.jarvis.code.core.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KimChheng on 5/29/2017.
 */

public abstract class AbstractEntity  implements Serializable{

    @SerializedName("ID")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
