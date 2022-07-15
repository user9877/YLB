package com.bjpowernode.dataservice.service.impl.user;

import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.api.service.user.UserService;
import com.bjpowernode.dataservice.mapper.UserMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl.user
 * Date:2022/7/15 18:08
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = UserService.class,version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //根据主键查询用户和资金
    @Override
    public UserAccountModel queryAllInfoByUid(Integer uid) {
        UserAccountModel userAccountModel = null;
        if(AppUtil.checkuserId(uid)){
            userAccountModel = userMapper.selectUserAccountById(uid);

        }
        return userAccountModel;
    }
}
