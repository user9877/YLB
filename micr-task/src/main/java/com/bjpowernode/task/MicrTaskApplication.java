package com.bjpowernode.task;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class MicrTaskApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MicrTaskApplication.class, args);
        TaskManager tm = (TaskManager) ctx.getBean("taskManager");
        //tm.taskIncomePlan();
        tm.taskRechargeResult();
    }

}
