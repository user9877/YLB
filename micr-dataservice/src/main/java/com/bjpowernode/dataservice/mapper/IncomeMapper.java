package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.Income;
import com.bjpowernode.api.model.ProductIncomeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomeMapper {

    int insert(Income record);
    //查询用户收益
    List<ProductIncomeModel> selectIncomeByUserId(@Param("uid") Integer uid, @Param("offset") int offset, @Param("rows") Integer rows);
}
