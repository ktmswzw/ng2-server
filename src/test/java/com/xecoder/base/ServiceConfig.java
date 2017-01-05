package com.xecoder.base;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by vincent on 16/8/26.
 * Duser.name = 224911261@qq.com
 * habit-team-server
 */
@ConfigurationProperties(prefix="serviceConfig")
public class ServiceConfig {
    private String url;
    private String port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
