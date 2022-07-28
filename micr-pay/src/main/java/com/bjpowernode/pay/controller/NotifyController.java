package com.bjpowernode.pay.controller;

import com.bjpowernode.pay.service.KQRechargeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:NotifyController
 * Package:com.bjpowernode.pay.controller
 * Date:2022/7/27 16:15
 * Description:异步通知
 * Autor:FH
 */
@RestController
public class NotifyController {
    @Resource
    private KQRechargeService kqRechargeService;
    //接收快钱支付结果：异步通知
    //http://localhost:9000/pay/recharge/notify/kq?merchantAcctId=1001214035601&orderId=KQ202207281610346011&payAmount=1&payResult=10
    @GetMapping("/recharge/notify/kq")
    public String receKQNotify(HttpServletRequest request){
        //调用异步通知处理逻辑
        try {
            kqRechargeService.doNotify(request);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return "<result>1</result><redirecturl>http://localhost:8080<redirecturl>";
        }
    }
}
