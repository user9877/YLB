package com.bjpowernode.web.resp;

import com.bjpowernode.common.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ClassName:CommonResult
 * Package:com.bjpowernode.web.resp
 * Date:2022/7/6 18:23
 * Description:
 * Autor:FH
 */
@ApiModel(value = "统一应答结果")
public class CommonResult {

    //自定义的应答码
    @ApiModelProperty(value = "自定义应答码")
    private Integer code;
    //消息文本
    @ApiModelProperty(value = "应答码的文本说明")
    private String message;
    //数据
    @ApiModelProperty(value = "应答数据")
    private Object data;

    public void setResult(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getText();
    }

    //创建一个成功的CommonResult
    public static CommonResult OK(){
        CommonResult commonResult = new CommonResult();
        commonResult.setResult(ResultCode.SUCCESS);
        commonResult.setData("");
        return commonResult;
    }

    //创建一个失败的CommonResult
    public static CommonResult Fail(){
        CommonResult commonResult = new CommonResult();
        commonResult.setResult(ResultCode.FAILURE);
        commonResult.setData("");
        return commonResult;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CommonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult() {
    }
}
