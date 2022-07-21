package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.User;
import com.bjpowernode.api.model.UserAccountModel;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);
    //注册用户数
    int countRegisterUsers();
    //根据主键查询用户和资金
    UserAccountModel selectUserAccountById(@Param("id") Integer uid);
    //按手机号查询
    User selectByPhone(@Param("phone") String phone);
    //注册用户，同时返回id
    int insertUserReturnId(User newUser);

    User selectLogin(@Param("phone") String phone, @Param("loginPassword") String newPassword);
}

