package com.bjpowernode.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * ClassName:SwaggerConfig
 * Package:com.bjpowernode.settings
 * Date:2022/7/5 9:36
 * Description:
 * Autor:FH
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        //参数是swagger的版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //创建ApiInfo对象
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("盈利宝项目接口文档")
                .version("1.0。0")
                .description("盈利宝前后端分离微服务项目")
                .contact( new Contact("bj2203", //文档发布者
                        "http://www.bjpowernode.com",  //文档发布者网站
                        "bj2203@bj2203.cn")) //文档发布者的联系邮箱
                .build();
        //让docket使用ApiInfo对象
        docket.apiInfo(apiInfo);
        // 设置哪些包参与api文档的生成
        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.bjpowernode.controller")).build();
        //禁用文档生成
        //docket.enable(false);
        return docket;
    }
}
