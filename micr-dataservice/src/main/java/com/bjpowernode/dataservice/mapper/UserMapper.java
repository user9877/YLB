package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.User;

public interface UserMapper {
    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int countRegisterUsers();
}

