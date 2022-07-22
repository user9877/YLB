package com.bjpowernode.api.service;

import com.bjpowernode.api.model.ProductBidModel;

import java.util.List;

/**
 * ClassName:InvestService
 * Package:com.bjpowernode.api.service
 * Date:2022/7/15 17:35
 * Description:
 * Autor:FH
 */

public interface InvestService {
    //查询产品投资记录
    List<ProductBidModel> queryBidByProductId(Integer productId,Integer pageNo,Integer pageSize);
    //查询某个用户的投资记录
    List<ProductBidModel> queryBidByUserId(Integer uid,Integer pageNo,Integer pageSize);

}
