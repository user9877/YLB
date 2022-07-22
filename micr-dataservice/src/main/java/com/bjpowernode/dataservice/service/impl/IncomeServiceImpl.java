package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.Income;
import com.bjpowernode.api.model.ProductIncomeModel;
import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.dataservice.mapper.IncomeMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:IncomeServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/22 18:20
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = IncomeService.class,version = "1.0.0")
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private IncomeMapper incomeMapper;

    //查询用户收益
    @Override
    public List<ProductIncomeModel> queryIncomeByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<ProductIncomeModel> incomeList = new ArrayList<>();
        if(AppUtil.checkProductId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            incomeList = incomeMapper.selectIncomeByUserId(uid,offset,pageSize);
        }
        return incomeList;
    }
}
