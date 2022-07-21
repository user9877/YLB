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
    FAILURE(-1000,"请稍后重试！"),

    //vue和web服务之间请求错误，1000<code<3000
    FRONT_REQ_PARAM(1001,"请求参数不正确"),
    FRONT_PHONE_FORMAT(1002,"手机号格式不正确"),
    FRONT_SMS_FAIL(1003,"短信发送失败"),
    FRONT_SMS_EXIST(1004,"验证码可以继续使用"),
    FRONT_CODE_INVALID(1005,"无效的验证码"),
    FRONT_HAS_REALNAME(1006,"不能重复认证"),
    FRONT_REALNAME_FAIL(1007,"认证失败"),

    FRONT_TOKEN_INVALID(2999,"登录信息已失效,请重新登录"),

    //dubbo消费者和提供者之间业务处理错误，3000<=code<7000
    DUBBO_PARAM_ERROR(3000,"服务参数不正确"),
    DUBBO_PARAM_SUCCESS(3001,"远程调用成功"),
    DUBBO_PHONE_EXITS(3002,"手机号已被注册"),
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
