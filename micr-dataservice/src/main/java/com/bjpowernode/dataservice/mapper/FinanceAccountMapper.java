package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.FinanceAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface FinanceAccountMapper {
    int insert(FinanceAccount record);

    FinanceAccount selectByUidForUpdate(@Param("uid") Integer uid);
    //购买理财产品，扣除金额
    int updateAvailableMoney(@Param("uid") Integer uid, @Param("investMoney") BigDecimal investMoney);

    int updateAvailableMoneyByIncomeBack(@Param("uid") Integer uid, @Param("bidMoney") BigDecimal bidMoney, @Param("incomeMoney") BigDecimal incomeMoney);
}
