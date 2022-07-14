package com.bjpowernode.web.controller;

import com.bjpowernode.api.service.ProductService;
import com.bjpowernode.api.service.YlbBaseService;
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
    /*声明拼台接口服务*/
    @DubboReference(interfaceClass = YlbBaseService.class,version = "1.0.0")
    protected YlbBaseService ylbBaseService;
    //声明产品服务对象
    @DubboReference(interfaceClass = ProductService.class,version = "1.0.0")
    protected ProductService productService;


}
