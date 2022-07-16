package com.bjpowernode.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:JdwxSmsConfig
 * Package:com.bjpowernode.web.config
 * Date:2022/7/16 16:46
 * Description:
 * Autor:FH
 */
@Component
@ConfigurationProperties(prefix = "jdwx.sms")
public class JdwxSmsConfig {
    private String url;
    private String regContent;
    private String loginContent;

    public JdwxSmsConfig() {
    }

    public JdwxSmsConfig(String url, String regContent, String loginContent, String appkey) {
        this.url = url;
        this.regContent = regContent;
        this.loginContent = loginContent;
        this.appkey = appkey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegContent() {
        return regContent;
    }

    public void setRegContent(String regContent) {
        this.regContent = regContent;
    }

    public String getLoginContent() {
        return loginContent;
    }

    public void setLoginContent(String loginContent) {
        this.loginContent = loginContent;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    private String appkey;
}
