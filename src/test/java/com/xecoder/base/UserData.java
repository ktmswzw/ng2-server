package com.xecoder.base;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by vincent on 16/8/26.
 * Duser.name = 224911261@qq.com
 * habit-team-server
 */
@ConfigurationProperties(prefix = "userdata",locations = "classpath:test.properties")
public class UserData {
    private String username;
    private String password;
    private String device;
    private String deviceToken;
    private String habitId;
    private String groupId;
    private String clientVsersion;
    private String developId1;
    private String developId2;
    private String activityId;
    private String stickersId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClientVsersion() {
        return clientVsersion;
    }

    public void setClientVsersion(String clientVsersion) {
        this.clientVsersion = clientVsersion;
    }

    public String getDevelopId1() {
        return developId1;
    }

    public void setDevelopId1(String developId1) {
        this.developId1 = developId1;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStickersId() {
        return stickersId;
    }

    public void setStickersId(String stickersId) {
        this.stickersId = stickersId;
    }

    public String getDevelopId2() {
        return developId2;
    }

    public void setDevelopId2(String developId2) {
        this.developId2 = developId2;
    }
}
