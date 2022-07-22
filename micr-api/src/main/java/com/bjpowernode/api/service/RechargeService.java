package com.bjpowernode.api.service;

import com.bjpowernode.api.domain.Recharge;

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
}
