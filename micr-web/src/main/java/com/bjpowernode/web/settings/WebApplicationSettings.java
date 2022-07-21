package com.bjpowernode.web.settings;

import com.bjpowernode.interceptor.TokenInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * ClassName:WebApplicationSettings
 * Package:com.bjpowernode.web.settings
 * Date:2022/7/20 21:10
 * Description:
 * Autor:FH
 */
@Configuration
public class WebApplicationSettings implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象
        TokenInterceptors tokenInterceptors = new TokenInterceptors(stringRedisTemplate);
        //添加拦截器对象
        String[] addPath = {"/v1/user/realname"};
        registry.addInterceptor(tokenInterceptors).addPathPatterns(addPath);
    }
}
