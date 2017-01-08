package com.xecoder.config;

import com.xecoder.common.utils.BaseBean;
import com.xecoder.common.utils.DeviceType;
import com.xecoder.common.utils.JWTCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2016/1/11-10:26
 * Feeling.com.xecoder.controller
 */
@ControllerAdvice
public class BaseController {

    public static String USERID_STR = "userId";
    private static String SEPARATOR = "-";
    public static String VERSION_STR = "CLIENT-VERSION";
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    private String userId;
    private String jwt;
    public BaseBean baseBean;


    /**
     * 应用版本号
     */
    private String version;
    /**
     * 设备唯一标示
     */
    private String udid;

    private DeviceType deviceType;

    private String version_str;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.jwt = request.getHeader(JWTCode.AUTHORIZATION_STR);
        this.version_str = request.getHeader(VERSION_STR);
    }


    public void setDeviceVersion(String versionStr) {
        if (versionStr != null && versionStr.contains(SEPARATOR)) {
            String[] strs = versionStr.split(SEPARATOR);
            if (strs.length == 3) {
                this.deviceType = DeviceType.getType(strs[0]);
                this.udid = strs[1];
                this.version = strs[2];
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static String getVersionStr() {
        return VERSION_STR;
    }

    public static void setVersionStr(String versionStr) {
        VERSION_STR = versionStr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public static String getUseridStr() {
        return USERID_STR;
    }

    public static void setUseridStr(String useridStr) {
        USERID_STR = useridStr;
    }

    public BaseBean getBaseBean() {
        return baseBean;
    }

    public void setBaseBean(BaseBean baseBean) {
        this.baseBean = baseBean;
    }

    public String getVersion_str() {
        return version_str;
    }

    public void setVersion_str(String version_str) {
        this.version_str = version_str;
    }
}
