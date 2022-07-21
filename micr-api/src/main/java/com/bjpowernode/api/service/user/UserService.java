package com.bjpowernode.api.service.user;

import com.bjpowernode.api.domain.User;
import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.api.result.RPCResult;

/**
 * ClassName:UserService
 * Package:com.bjpowernode.api.service.user
 * Date:2022/7/15 18:03
 * Description:
 * Autor:FH
 */

public interface UserService {
    //根据主键查询用户和资金
    UserAccountModel queryAllInfoByUid(Integer uid);
    //用户注册功能
    RPCResult registerUser(String phone, String loginPassword);

    User userLogin(String phone, String loginPassword);
}
