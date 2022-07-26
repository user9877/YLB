package com.bjpowernode.task;

import com.bjpowernode.api.service.IncomeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName:TaskManager
 * Package:com.abc.task
 * Description: 需要定时处理的业务
 */
@Component("taskManager")
public class TaskManager {

    @DubboReference(interfaceClass = IncomeService.class,version = "1.0.0")
    private IncomeService incomeService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void taskIncomePlan() {
        System.out.println("生成收益计划------开始");
        incomeService.generateIncomePlan();
        System.out.println("生成收益计划------完成");
    }
    @Scheduled(cron = "0 0 1 * * ?")
    public void taskIncomeBack() {
        System.out.println("收益返还------开始");
        incomeService.generateIncomeBack();
        System.out.println("收益返还------完成");
    }
}
