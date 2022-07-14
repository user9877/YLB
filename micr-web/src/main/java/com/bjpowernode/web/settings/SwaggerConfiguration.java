package com.bjpowernode.web.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * ClassName:SwaggerConfiguration
 * Package:com.bjpowernode.web.settings
 * Date:2022/7/8 21:06
 * Description:
 * Autor:FH
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        // 文档类型 2.0版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("动力节点盈利宝项目")
                .version("1.0.0")
                .description("盈利宝前后端分离微服务项目")
                .build();
        docket.apiInfo(apiInfo);
        // 指定注解
        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.bjpowernode.web.controller")).build();
        return docket;
    }
}
