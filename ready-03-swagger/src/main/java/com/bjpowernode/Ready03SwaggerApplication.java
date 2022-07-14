package com.bjpowernode;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//启动swagger功能，生成接口文档
@EnableSwagger2
//新的UI
@EnableSwaggerBootstrapUI
@SpringBootApplication
public class Ready03SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ready03SwaggerApplication.class, args);
    }

}
