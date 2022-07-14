package com.bjpowernode.web;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwaggerBootstrapUI
@EnableSwagger2
@EnableDubbo
@SpringBootApplication
public class MicrWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrWebApplication.class, args);
    }

}
