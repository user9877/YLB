package com.bjpowernode.dataservice.service.impl.user;

import com.bjpowernode.api.domain.FinanceAccount;
import com.bjpowernode.api.domain.User;
import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.api.service.user.UserService;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

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
    @Autowired
    private FinanceAccountMapper accountMapper;
    @Value("${app.password.salt}")
    private String loginPasswordSalt;
    //根据主键查询用户和资金
    @Override
    public UserAccountModel queryAllInfoByUid(Integer uid) {
        UserAccountModel userAccountModel = null;
        if(AppUtil.checkuserId(uid)){
            userAccountModel = userMapper.selectUserAccountById(uid);

        }
        return userAccountModel;
    }
    //用户注册功能
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized RPCResult registerUser(String phone, String loginPassword) {
        RPCResult rpcResult = new RPCResult(ResultCode.DUBBO_PARAM_ERROR.getCode(),
                ResultCode.DUBBO_PARAM_ERROR.getText(), "");
        if(AppUtil.checkPhone(phone) && loginPassword != null){
            //查询手机号是否注册过
            User user = userMapper.selectByPhone(phone);
            if(user == null){
                //没有注册过,可以注册
                //把用户信息添加到u_user表，同时把新的用户信息的id添加到u_finance_account
                //密码加盐(自定义一个字符串参与密码的二次加密)
                String newPassword = DigestUtils.md5Hex(loginPassword+loginPasswordSalt);
                User newUser = new User();
                newUser.setPhone(phone);
                newUser.setLoginPassword(loginPassword);
                newUser.setAddTime(new Date());
                userMapper.insertUserReturnId(newUser);
                //注册account
                FinanceAccount account = new FinanceAccount();
                account.setUid(newUser.getId());
                account.setAvailableMoney(new BigDecimal("0"));
                accountMapper.insert(account);
                rpcResult = new RPCResult(ResultCode.DUBBO_PARAM_SUCCESS.getCode(),
                        ResultCode.DUBBO_PARAM_SUCCESS.getText(), newUser);
            }else{
                rpcResult = new RPCResult(ResultCode.DUBBO_PHONE_EXITS.getCode(),
                        ResultCode.DUBBO_PHONE_EXITS.getText(), user);
            }
        }
        return rpcResult;
    }
}
