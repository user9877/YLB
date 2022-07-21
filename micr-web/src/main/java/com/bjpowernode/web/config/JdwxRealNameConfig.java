package com.bjpowernode.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:JdwxRealNameConfig
 * Package:com.bjpowernode.web.config
 * Date:2022/7/21 15:13
 * Description:
 * Autor:FH
 */
@ConfigurationProperties(prefix = "jdwx.realname")
@Component
public class JdwxRealNameConfig {
    private String url;
    private String appkey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
