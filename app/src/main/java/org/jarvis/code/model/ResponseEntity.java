package org.jarvis.code.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KimChheng on 5/29/2017.
 */

public class ResponseEntity<T> implements Serializable {

    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private Integer code;
    @SerializedName("STATE")
    private Boolean state;
    @SerializedName("HTTP_STATUS")
    private String httpStatus;
    @SerializedName("DATA")
    private List<T> data = new ArrayList<>();
    @SerializedName("MAP")
    private Map<String, T> map = new HashMap();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }
}
