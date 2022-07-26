package com.bjpowernode.api.service;

import com.bjpowernode.api.model.ProductIncomeModel;

import java.util.List;

/**
 * ClassName:IncomeService
 * Package:com.bjpowernode.api.service
 * Date:2022/7/22 18:19
 * Description:
 * Autor:FH
 */

public interface IncomeService {
    //查询用户收益
    List<ProductIncomeModel> queryIncomeByUid(Integer uid, Integer pageNo, Integer pageSize);

    //生成收益计划
    void generateIncomePlan();
    //收益返还
    void generateIncomeBack();
}
