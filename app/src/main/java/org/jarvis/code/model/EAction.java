package org.jarvis.code.model;

/**
 * Created by ki.kao on 9/28/2017.
 */

public enum EAction {
    NEW(1, "NEW"),
    UPDATE(2, "UPDATE"),
    DELETE(3, "DELETE");

    int code;
    String desc;

    EAction(int code, String desc) {
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
