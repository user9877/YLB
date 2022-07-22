package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.BidInfo;
import com.bjpowernode.api.model.ProductBidModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BidInfoMapper {
    int insertSelective(BidInfo record);
    //累计投资金额
    BigDecimal selectSumAllBidMoney();
    //查询某个产品最近的投资记录
    List<ProductBidModel> selectBidsByProductId(@Param("productId") Integer productId, @Param("offset") int offset, @Param("rows") Integer rows);
    //查询某个用户的投资记录
    List<ProductBidModel> selectBidsByUserId(@Param("uid") Integer uid, @Param("offset") int offset, @Param("rows") Integer rows);
}
