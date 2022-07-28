package com.bjpowernode.api.service;

import com.bjpowernode.api.domain.Recharge;
import com.bjpowernode.api.result.RPCResult;

import java.util.List;

/**
 * ClassName:RechargeService
 * Package:com.bjpowernode.api.service
 * Date:2022/7/22 17:37
 * Description:
 * Autor:FH
 */

public interface RechargeService {
    //查询用户的充值记录
    List<Recharge> queryRechargeByUid(Integer uid,Integer pageNo,Integer pageSize);
    //创建充值记录
    boolean addRecharge(Recharge recharge);
    //处理异步通知
    RPCResult handleRechargeNofity(String orderId, String payResult, String payAmount);
}
