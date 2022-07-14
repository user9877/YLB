package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.BidInfo;

import java.math.BigDecimal;

public interface BidInfoMapper {
    int insertSelective(BidInfo record);

    BigDecimal selectSumAllBidMoney();
}
