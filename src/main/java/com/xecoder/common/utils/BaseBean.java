package com.xecoder.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2016/1/11-10:15
 * Feeling.com.xecoder.model
 */
public class BaseBean implements Serializable {

    private static final long serialVersionUID = -8947050853977349957L;

    @Transient
    public String errorCode = "0";		//錯誤代碼，0為無錯誤

    @JsonIgnore
    private String baseCreator;

    @JsonIgnore
    private String baseLastModifier;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date baseCreateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date baseLastModifyTime;

    public BaseBean() {
        if(this.baseCreateTime==null)
            baseCreateTime = new Date();
        baseLastModifyTime = new Date();
    }

    public String getBaseCreator() {
        return baseCreator;
    }

    public void setBaseCreator(String baseCreator) {
        this.baseCreator = baseCreator;
    }

    public String getBaseLastModifier() {
        return baseLastModifier;
    }

    public void setBaseLastModifier(String baseLastModifier) {
        this.baseLastModifier = baseLastModifier;
    }

    public Date getBaseCreateTime() {
        return baseCreateTime;
    }

    public void setBaseCreateTime(Date baseCreateTime) {
        this.baseCreateTime = baseCreateTime;
    }

    public Date getBaseLastModifyTime() {
        return baseLastModifyTime;
    }

    public void setBaseLastModifyTime(Date baseLastModifyTime) {
        this.baseLastModifyTime = baseLastModifyTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
