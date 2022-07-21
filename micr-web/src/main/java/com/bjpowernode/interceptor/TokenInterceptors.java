package com.bjpowernode.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.web.resp.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ClassName:TokenInterceptors
 * Package:com.bjpowernode.interceptor
 * Date:2022/7/20 20:40
 * Description:
 * Autor:FH
 */

public class TokenInterceptors implements HandlerInterceptor {
    private StringRedisTemplate redisTemplate;
    public TokenInterceptors(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean checkTokenResult = false;
        //检查token是否有效
        if("OPTIONS".equals(request.getMethod())){
            return true;
        }
        //其他请求方式
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(authorization) && authorization.contains("Bearer")){
            //从请求头中取出token
            String token = authorization.substring(7);
            if(StringUtils.isNotBlank(token)){//请求头中有token
                String key = RedisKeyContants.TOKEN_ACCESS + token;
                if(redisTemplate.hasKey(key)){//如果redis中有key
                    Object saveUid = redisTemplate.boundHashOps(key).get("uid");
                    if(saveUid != null){
                        if(request.getHeader("uid").equals(saveUid)){
                        //把请求头重的uid和redis中的对比
                            checkTokenResult = true;
                        }
                    }
                }
            }
        }
        if(checkTokenResult == false){
            CommonResult cr = CommonResult.Fail();
            cr.setResult(ResultCode.FRONT_TOKEN_INVALID);
            //把cr对象转为json字符串
            String json  = JSONObject.toJSONString(cr);
            //使用HttpServletResponse输出json
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            out.close();
        }
        return checkTokenResult;
    }
}
