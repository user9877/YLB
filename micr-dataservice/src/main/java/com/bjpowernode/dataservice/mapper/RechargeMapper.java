package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.Recharge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeMapper {
    int insert(Recharge record);

    List<Recharge> selectByUid(@Param("uid") Integer uid, @Param("offset") int offset, @Param("rows") Integer rows);

    Recharge selectByOrderId(@Param("orderId") String orderId);

    int updateStatus(@Param("id") Integer id, @Param("rechargeStatusSuccess") int rechargeStatusSuccess);
}
