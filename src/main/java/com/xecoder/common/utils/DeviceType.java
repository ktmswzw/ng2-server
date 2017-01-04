package com.xecoder.common.utils;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2016/3/24-10:02
 * habit-team-server.com.imakehabits.model.embedded
 */
public enum DeviceType {
    IOS("0"),
    ANDROID("1"),
    OTHER("2");

    private String value;

    DeviceType(String value) {
        this.value = value;
    }

    public static DeviceType getType(String value) {
        for (DeviceType type : DeviceType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return OTHER;
    }
}
