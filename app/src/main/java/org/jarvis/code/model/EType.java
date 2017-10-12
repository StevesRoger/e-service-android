package org.jarvis.code.model;

/**
 * Created by ki.kao on 9/28/2017.
 */

public enum EType {
    PRODUCT(1, "PRODUCT"),
    PROMOTION(2, "PROMOTION"),
    ADVERTISEMENT(3, "ADVERTISEMENT");

    int code;
    String desc;

    EType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
