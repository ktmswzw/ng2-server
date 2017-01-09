package com.xecoder.common.exception;

import com.alibaba.fastjson.JSONObject;

public class SystemErrorMsg{

    private Integer apistatus;

    private JSONObject result;

    public SystemErrorMsg() {
        this.apistatus = 1;
    }

    public Integer getApistatus() {
        return apistatus;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }
}
