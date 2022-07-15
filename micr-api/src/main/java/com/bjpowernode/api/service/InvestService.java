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
    List<ProductBidModel> queryBidByProductId(Integer productId,Integer pageNo,Integer pageSize);
}
