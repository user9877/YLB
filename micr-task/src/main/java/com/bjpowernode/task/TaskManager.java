package com.bjpowernode.task;

import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.util.HttpClientUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.client.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    //定时查询支付结果
    @Scheduled(cron = "0 0/20 * * * ?")
    public void taskRechargeResult()  {
        System.out.println("支付结果查询------开始:"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        try {
            HttpClientUtils.doGet("http://localhost:9000/pay/recharge/query");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("支付结果查询------完成:"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }
}
