package com.xecoder.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xecoder.common.utils.*;
import com.xecoder.config.BaseController;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2016/1/11-16:03
 * Feeling.com.xecoder.model.business
 */

public class AuthToken extends BaseBean implements Serializable {

    private static final long serialVersionUID = -3373371149561708376L;
    /**
     * 令牌有效期
     */
    public static long EXPIRED_TIME = 30 * 24 * 3600 * 1000L;
    /**
     * 通过验证的设备
     */
    private DeviceEnum device;
    /**
     * 令牌
     */
    private String token;
    /**
     * 令牌获取的时间
     */
    private Date timestamp = new Date();
    /**
     * 令牌所属的用户
     */
    @JsonIgnore
    private Long userid;

    private String deviceToken;


    @JsonIgnore
    private String jwt;

    public AuthToken(User user, DeviceEnum device) {
        this.token = getRandomToken();
        this.jwt = getJWT(this.token,user.getId());
        this.timestamp = new Date();
        this.userid = user.getId();
        this.device = device;
    }

    private String getRandomToken(){
        return Encodes.encodeHex(Digests.sha1((RandomUtils.getRadomByte().toString()).getBytes()));
    }

    private String getJWT(String token,Long userId)
    {

        //https://github.com/auth0/java-jwt
        //JWT http://blog.leapoahead.com/2015/09/06/understanding-jwt/
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put(BaseController.TOKEN_STR,token);
        claims.put("iss","xecoder.com");
        claims.put("exp",(System.currentTimeMillis() + this.EXPIRED_TIME )/ 1000L);
        String jwt = JWTCode.SIGNER.sign(claims);
        System.out.println("jwt = " + jwt);
        return jwt;
    }

    public AuthToken(){}

    public DeviceEnum getDevice() {
        return device;
    }

    public void setDevice(DeviceEnum device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.timestamp.getTime() > EXPIRED_TIME;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
