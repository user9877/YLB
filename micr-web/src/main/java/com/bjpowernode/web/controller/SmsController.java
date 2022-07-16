package com.bjpowernode.web.controller;

import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:SmsController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/16 16:14
 * Description:
 * Autor:FH
 */
@Api(tags = "短信功能")
@RestController
public class SmsController extends BaseController{

    @ApiOperation(value = "注册验证码")
    @GetMapping("/v1/sms/code/reg")
    public CommonResult sendSmsCodeRegister(String phone){
        CommonResult cr = CommonResult.Fail();
        if(AppUtil.checkPhone(phone)){
            //检查验证码是否可用
            boolean isExist = smsService.checkSmsCodeRegister(phone);
            if(isExist){
                cr.setResult(ResultCode.FRONT_SMS_EXIST);
            }else{
                //发送短信
                boolean handleSms = smsService.sendSmsCodeRegister(phone);
                if(handleSms){
                    cr = CommonResult.OK();
                }else{
                    cr.setResult(ResultCode.FRONT_SMS_FAIL);
                }
            }
        }else{
            cr.setResult(ResultCode.FRONT_PHONE_FORMAT);
        }
        return cr;
    }
}
