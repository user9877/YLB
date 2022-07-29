package com.bjpowernode.web.controller;

import cn.hutool.core.date.DateUtil;
import com.bjpowernode.api.domain.User;
import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.model.RealNameVO;
import com.bjpowernode.web.model.UserParam;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.view.UserAccountView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/18 14:52
 * Description:
 * Autor:FH
 */
@Api(tags = "用户登录，注册")
@RestController
public class UserController extends BaseController {
    //注册
    @PostMapping("/v1/user/register")
    public CommonResult userRegister(@RequestBody UserParam userParam) {
        CommonResult cr = CommonResult.Fail();
        if (userParam.checkData()) {
            //参数正确，可以注册
            //1.检查验证码是否有效
            boolean isValid = smsService.checkSmsCodeRegisterValid(userParam.getPhone(), userParam.getVerificationCode());
            if (isValid) {
                //验证码有效，可以注册
                RPCResult rpcResult = userService.registerUser(userParam.getPhone(), userParam.getLoginPassword());
                if (rpcResult.getCode() == ResultCode.DUBBO_PARAM_SUCCESS.getCode()) {
                    cr = CommonResult.OK();
                } else {
                    cr.setMessage(rpcResult.getText());
                }
            } else {
                cr.setResult(ResultCode.FRONT_CODE_INVALID);
            }
        } else {
            cr.setResult(ResultCode.FRONT_REQ_PARAM);
        }
        return cr;
    }

    //获取访问token,相当于登录
    @ApiOperation(value = "访问token")
    @PostMapping("/v1/token/access")
    public CommonResult accessToken(@RequestBody UserParam userParam,
                                    HttpServletRequest request) {
        CommonResult cr = CommonResult.Fail();
        if (userParam.checkData()) {
            //检查验证码是否有效
            boolean isValid = smsService.checkSmsCodeLoginValid(userParam.getPhone(), userParam.getVerificationCode());
            if (isValid) {
                //登录系统，生成token
                User user = userService.userLogin(userParam.getPhone(), userParam.getLoginPassword());
                if (user != null) {
                    //判断是否有token
                    String accessToken = stringRedisTemplate.boundValueOps(RedisKeyContants.TOKEN_USER + user.getId()).get();
                    if (StringUtils.isEmpty(accessToken)) {//如果为空，重新生成token
                        //登录成功，生成token,md5(userId+","+ip+","+登录时间毫秒值)
                        String src = user.getId() + "," +
                                request.getRemoteAddr() + "," +
                                (new Date().getTime());
                        accessToken = DigestUtils.md5Hex(src);
                        accessToken = accessToken.toUpperCase();
                        System.out.println("token=" + accessToken);
                        //存放到redis中
                        String tokenKey = RedisKeyContants.TOKEN_ACCESS + accessToken;
                        BoundHashOperations<String, Object, Object> ops =
                                stringRedisTemplate.boundHashOps(tokenKey);
                        Map<String, String> tokenData = new HashMap<>();
                        tokenData.put("uid", String.valueOf(user.getId()));
                        tokenData.put("ip", request.getRemoteAddr());
                        tokenData.put("loginTime", DateUtil.
                                format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        //token存储的数据
                        ops.putAll(tokenData);
                        //设置过期时间
                        ops.expire(60, TimeUnit.MINUTES);
                        //把userId和token存到redis
                        stringRedisTemplate.
                                opsForValue().
                                set(RedisKeyContants.TOKEN_USER + user.getId(),accessToken,60,TimeUnit.MINUTES);
                    }
                    //如果不为空，直接把数据返回到前端
                    cr = CommonResult.OK();
                    Map<String, Object> respData = new HashMap<>();
                    respData.put("uid", user.getId());
                    respData.put("name", user.getName());
                    respData.put("phone", user.getPhone());
                    respData.put("accessToken", accessToken);
                    cr.setData(respData);
                }
            } else {
                cr.setResult(ResultCode.FRONT_CODE_INVALID);
            }
        }
        return cr;
    }

    //实名认证
    @ApiOperation(value = "实名认证")
    @PostMapping("/v1/user/realname")
    public CommonResult realName(@RequestHeader("uid") Integer uid,
                                 @RequestBody RealNameVO realNameVO){
        CommonResult cr = CommonResult.OK();
        cr.setResult(ResultCode.FRONT_REQ_PARAM);
        if(realNameVO.checkData()){
            UserAccountModel userAccountModel = userService.queryAllInfoByUid(uid);
            //判断用户是否已经进行过实名认证
            if(userAccountModel != null &&
                    userAccountModel.getPhone().equals(realNameVO.getPhone())){
                if(StringUtils.isBlank(userAccountModel.getName())){
                    //需要实名认证
                    boolean realname = realNameService.handleRealName(uid,realNameVO.getName(),realNameVO.getIdCard());
                    if(realname){
                        cr = CommonResult.OK();
                    }else{
                        cr.setResult(ResultCode.FRONT_REALNAME_FAIL);
                    }
                }else{
                    cr.setResult(ResultCode.FRONT_HAS_REALNAME);
                }
            }
        }
        return cr;
    }

    //用户中心,用户基本信息
    @ApiOperation(value = "用户中心,用户基本信息")
    @GetMapping("/v1/user/center/info")
    public CommonResult userCenter(@RequestHeader("uid") Integer uid){
        CommonResult cr = CommonResult.Fail();
        if(AppUtil.checkuserId(uid)){
            UserAccountModel userAccountModel = userService.queryAllInfoByUid(uid);
            if(userAccountModel != null){
                UserAccountView view = new UserAccountView(userAccountModel);
                cr = CommonResult.OK();
                cr.setData(view);
            }else{
                cr.setResult(ResultCode.FRONT_USER_NOT_EXIST);
            }
        }
        return cr;
    }

    //退出
    @GetMapping("/v1/user/logout")
    public CommonResult userLogout(@RequestHeader("uid") Integer uid,
                                   @RequestHeader("Authorization") String authorization){
        CommonResult cr = CommonResult.Fail();
        if(StringUtils.isNotBlank(authorization) && authorization.contains("Bearer")){
            //从请求头中取出token
            String token = authorization.substring(7);
            if(StringUtils.isNotBlank(token)){
                String key = RedisKeyContants.TOKEN_ACCESS + token;
                Boolean deleteAccessToken = true;
                //删除redis中的token
                if(stringRedisTemplate.hasKey(key)){
                     deleteAccessToken = stringRedisTemplate.delete(key);
                }
                String userIdKey = RedisKeyContants.TOKEN_USER + uid;
                Boolean deleteUserToken = true;
                if(stringRedisTemplate.hasKey(userIdKey)){
                     deleteUserToken = stringRedisTemplate.delete(userIdKey);
                }

                if(deleteAccessToken && deleteUserToken){
                    cr = CommonResult.OK();
                }
            }
        }
        return cr;
    }
}
