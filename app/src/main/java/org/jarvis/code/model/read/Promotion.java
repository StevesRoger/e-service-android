package org.jarvis.code.model.read;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ki.kao on 8/26/2017.
 */

public class Promotion extends Product {

    @SerializedName("DESC")
    private String desc;

    public Promotion(Parcel source) {
        super();
        desc = source.readString();
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(desc);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
