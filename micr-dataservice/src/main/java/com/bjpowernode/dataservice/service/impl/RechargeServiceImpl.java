package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.Recharge;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.dataservice.mapper.RechargeMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:RechargeServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/22 17:39
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = RechargeService.class,version = "1.0.0")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Override
    public List<Recharge> queryRechargeByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<Recharge> rechargeList = new ArrayList<>();
        if(AppUtil.checkuserId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1)*pageSize;
            rechargeList = rechargeMapper.selectByUid(uid,offset,pageSize);
        }
        return rechargeList;
    }
}
