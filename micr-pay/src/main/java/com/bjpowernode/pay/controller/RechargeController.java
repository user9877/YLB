package com.bjpowernode.pay.controller;

import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.pay.service.KQRechargeService;
import com.bjpowernode.util.AppUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * ClassName:RechargeController
 * Package:com.bjpowernode.pay.controller
 * Date:2022/7/27 15:59
 * Description:
 * Autor:FH
 */
@Controller
public class RechargeController {
    @Resource
    private KQRechargeService kqRechargeService;

    //支付接口：生成订单记录,生成表单信息发送给快钱的表单验证界面
    @GetMapping("/recharge/entry")
    public String receFromFrontRecharge(@RequestParam Integer uid,
                                        @RequestParam BigDecimal rechargeMoney,
                                        @RequestParam String channel,
                                        Model model) {
        String view = "errView";
        try {
            if(AppUtil.checkuserId(uid) && rechargeMoney !=null
                    && rechargeMoney.doubleValue() >= 0.01){
                UserAccountModel user = kqRechargeService.queryUserByUid(uid);
                if(user != null){
                    //生成参数，快钱支付接口所需参数
                    Map<String, String> from = kqRechargeService.generateFrom(uid, user.getPhone(), rechargeMoney);
                    //生成订单记录
                    kqRechargeService.addRecharge(uid,from.get("orderId"),rechargeMoney,channel);
                    //把订单号添加到redis
                    kqRechargeService.addOrderIdToRedis(from.get("orderId"));
                    model.addAllAttributes(from);
                    view = "kqForm";
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return view;
    }
    //支付结果查询接口
    @GetMapping("/recharge/query")
    @ResponseBody
    public String kqQuery(){
        kqRechargeService.doQuery();
        return "调用查询接口";
    }
}
