package org.jarvis.code.model.read;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KimChheng on 5/29/2017.
 */

public abstract class BaseResponse implements Serializable, Parcelable {

    @SerializedName("ID")
    protected Integer id;

    public BaseResponse() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
