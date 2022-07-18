package com.bjpowernode.web.controller;

import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.web.model.UserParam;
import com.bjpowernode.web.resp.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/18 14:52
 * Description:
 * Autor:FH
 */
@Api(tags = "用户登录，注册")
@CrossOrigin
@RestController
public class UserController extends BaseController {
    @PostMapping("/v1/user/register")
    public CommonResult userRegister(@RequestBody UserParam userParam){
        CommonResult cr = CommonResult.Fail();
        if(userParam.checkData()){
            //参数正确，可以注册
            //1.检查验证码是否有效
            boolean isValid = smsService.checkSmsCodeRegisterValid(userParam.getPhone(),userParam.getVerificationCode());
            if(isValid){
                //验证码有效，可以注册
                RPCResult rpcResult = userService.registerUser(userParam.getPhone(),userParam.getLoginPassword());
                if(rpcResult.getCode() == ResultCode.DUBBO_PARAM_SUCCESS.getCode()){
                    cr = CommonResult.OK();
                }else{
                    cr.setMessage(rpcResult.getText());
                }
            }else{
                cr.setResult(ResultCode.FRONT_CODE_INVALID);
            }
        }else{
            cr.setResult(ResultCode.FRONT_REQ_PARAM);
        }
        return cr;
    }
}
