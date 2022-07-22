package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.model.ProductBidModel;
import com.bjpowernode.api.service.InvestService;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:InvestServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/15 17:38
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = InvestService.class,version = "1.0.0")
public class InvestServiceImpl implements InvestService {
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Override
    public List<ProductBidModel> queryBidByProductId(Integer productId, Integer pageNo, Integer pageSize) {
        List<ProductBidModel> bidModelList = new ArrayList<>();
        if(AppUtil.checkProductId(productId)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            bidModelList = bidInfoMapper.selectBidsByProductId(productId,offset,pageSize);
        }
        return bidModelList;
    }
    //查询某个用户的投资记录
    @Override
    public List<ProductBidModel> queryBidByUserId(Integer uid, Integer pageNo, Integer pageSize) {
        List<ProductBidModel> bidList = new ArrayList<>();
        if(AppUtil.checkProductId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            bidList = bidInfoMapper.selectBidsByUserId(uid,offset,pageSize);
        }
        return bidList;
    }
}
