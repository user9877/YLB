package com.bjpowernode.api.result;

import com.bjpowernode.common.enums.ResultCode;

import java.io.Serializable;

/**
 * ClassName:RPCResult
 * Package:com.bjpowernode.api.result
 * Date:2022/7/18 15:22
 * Description:Dubbo服务调用结果
 * Autor:FH
 */

public class RPCResult implements Serializable {
    private static final long serialVersionUID = -9003216272939842721L;
    private int code;
    private String text;
    private Object data;

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.text = resultCode.getText();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RPCResult() {
    }

    public RPCResult(int code, String text, Object data) {
        this.code = code;
        this.text = text;
        this.data = data;
    }
}
