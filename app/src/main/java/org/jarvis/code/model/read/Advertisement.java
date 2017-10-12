package org.jarvis.code.model.read;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ki.kao on 9/2/2017.
 */

public class Advertisement extends BaseResponse {

    @SerializedName("IMAGE")
    private Integer image;

    public Advertisement(Parcel source) {
        image = source.readInt();
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public static final Creator<Advertisement> CREATOR = new Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel source) {
            return new Advertisement(source);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
    }
}
