package com.xecoder.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemErrorMsg implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer apistatus;

    private Integer status;

    private String path;

    private String error;

    @JsonProperty(value = "error_en")
    private String errorEn;

    @JsonProperty(value = "error_code")
    private String errorCode;

    public SystemErrorMsg() {
        this.apistatus = 0;
    }

    public Integer getApistatus() {
        return apistatus;
    }

    public Integer getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorEn() {
        return errorEn;
    }

    public void setErrorEn(String errorEn) {
        this.errorEn = errorEn;
    }

    @JsonProperty
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
