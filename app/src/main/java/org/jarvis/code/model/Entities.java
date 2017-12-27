package org.jarvis.code.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by KimChheng on 12/27/2017.
 */

public class Entities extends BaseRequest {

    @SerializedName("TYPE")
    private String type;
    @SerializedName("ENTITIES")
    private HashMap<String, Object> map;

    public Entities() {
        super();
        map = new HashMap();
    }

    public Entities(Parcel source) {
        type = source.readString();
        map = (HashMap<String, Object>) source.readSerializable();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public void putValue(String key, Object value) {
        this.map.put(key, value);
    }

    public static final Creator<Entities> CREATOR = new Creator<Entities>() {
        @Override
        public Entities createFromParcel(Parcel parcel) {
            return new Entities(parcel);
        }

        @Override
        public Entities[] newArray(int size) {
            return new Entities[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeSerializable(map);
    }

    @Override
    public String toString() {
        return "Entities{" +
                "type='" + type + '\'' +
                ", map=" + map +
                '}';
    }
}
