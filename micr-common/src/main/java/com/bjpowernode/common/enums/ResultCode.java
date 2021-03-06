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

    FRONT_USER_NOT_EXIST(1008,"用户信息不存在"),
    FRONT_EXCEP_ARITH(1009,"计算的数据错误"),
    FRONT_EXCEP_NULL(1010,"数据无效"),
    FRONT_EXCEP_OTHER(1011,"未知的错误"),

    FRONT_TOKEN_INVALID(2999,"登录信息已失效,请重新登录"),

    //dubbo消费者和提供者之间业务处理错误，3000<=code<7000
    DUBBO_PARAM_ERROR(3000,"服务参数不正确"),
    DUBBO_PARAM_SUCCESS(3001,"远程调用成功"),
    DUBBO_PHONE_EXITS(3002,"手机号已被注册"),
    DUBBO_ACCOUNT_MONEY(3003,"余额不足"),
    DUBBO_PRODUCT_NULL(3004,"产品已售罄"),
    DUBBO_INVEST_MONEY_ERR(3005,"投资金额不正确"),
    DUBBO_RECHARGE_RECORD_NOON(3006,"订单充值记录不存在"),
    DUBBO_RECHARGE_OVER(3007,"已经处理完毕"),
    DUBBO_RECHARGE_MONEY_ERR(3008,"充值金额错误"),
    DUBBO_RECHARGE_SUCCESS(3009,"充值成功"),
    DUBBO_RECHARGE_FAIL(3010,"充值失败"),
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
