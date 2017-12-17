package org.jarvis.code.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jarvis.code.util.Constants;

import java.io.Serializable;

/**
 * Created by KimChheng on 5/29/2017.
 */

public abstract class BaseResponse implements Serializable, Parcelable, Cloneable {

    @SerializedName("ID")
    protected Integer id;
    @Expose(serialize = false, deserialize = false)
    protected String imgUrl = Constants.BASE_URL + "mobile/image/view/";

    public BaseResponse() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof BaseResponse) {
            BaseResponse tmp = (BaseResponse) obj;
            return this.id.equals(tmp.id);
        }
        return false;
    }
}
