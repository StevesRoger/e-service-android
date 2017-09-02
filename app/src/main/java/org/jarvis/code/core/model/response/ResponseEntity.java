package org.jarvis.code.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}
