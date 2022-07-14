package com.bjpowernode.common.enums;

/**
 * ClassName:ResultCode
 * Package:com.bjpowernode.common.enums
 * Date:2022/7/6 18:50
 * Description:
 * Autor:FH
 */

public enum ResultCode {
    SUCCESS(1000,"业务处理完成！"),
    //vue和web服务之间请求错误，1000<code<3000
    FAILURE(-1000,"请稍后重试！"),

    //dubbo消费者和提供者之间业务处理错误，3000<=code<7000
    DUBBO_PARAM_ERROR(3000,"服务参数不正确"),
    ;
    private Integer code;
    private String text;

    ResultCode(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
