package com.bjpowernode.web.controller;

import com.bjpowernode.api.service.InvestService;
import com.bjpowernode.api.service.ProductService;
import com.bjpowernode.api.service.YlbBaseService;
import com.bjpowernode.api.service.user.UserService;
import com.bjpowernode.web.service.RealNameService;
import com.bjpowernode.web.service.SmsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * ClassName:BaseController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/6 17:57
 * Description:
 * Autor:FH
 */

public class BaseController {
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    //短信服务
    @Resource
    protected SmsService smsService;
    //实名认证
    @Resource
    protected RealNameService realNameService;
    /*声明拼台接口服务*/
    @DubboReference(interfaceClass = YlbBaseService.class,version = "1.0.0")
    protected YlbBaseService ylbBaseService;
    //声明产品服务对象
    @DubboReference(interfaceClass = ProductService.class,version = "1.0.0")
    protected ProductService productService;
    //声明投资服务对象
    @DubboReference(interfaceClass = InvestService.class,version = "1.0.0")
    protected InvestService investService;
    //用户服务
    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    protected UserService userService;


}
