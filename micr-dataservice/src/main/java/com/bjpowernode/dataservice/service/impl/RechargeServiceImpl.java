package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.Recharge;
import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.common.Constants;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.RechargeMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:RechargeServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/22 17:39
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = RechargeService.class,version = "1.0.0")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private FinanceAccountMapper accountMapper;
    @Override
    public List<Recharge> queryRechargeByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<Recharge> rechargeList = new ArrayList<>();
        if(AppUtil.checkuserId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1)*pageSize;
            rechargeList = rechargeMapper.selectByUid(uid,offset,pageSize);
        }
        return rechargeList;
    }

    @Override
    public boolean addRecharge(Recharge recharge) {
        boolean add  = false;
        if(recharge != null){
            int rows = rechargeMapper.insert(recharge);
            if(rows > 0){
                add = true;
            }
        }
        return add;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized RPCResult handleRechargeNofity(String orderId, String payResult, String payAmount) {
        RPCResult result = new RPCResult();
        int rows = 0;
        Recharge recharge = rechargeMapper.selectByOrderId(orderId);
        if(recharge != null){
            if(recharge.getRechargeStatus() == Constants.RECHARGE_STATUS_0){
                String fen = recharge.getRechargeMoney().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
                if(fen.equals(payAmount)){
                    if("10".equals(payResult)){
                        //充值成功
                        //更新资金账号
                        rows = accountMapper.updateAvailableMoneyByRecharge(recharge.getUid(),recharge.getRechargeMoney());
                        if(rows < 1){
                            throw new RuntimeException("账户资金更新失败");
                        }
                        //更新充值记录的状态
                        rows = rechargeMapper.updateStatus(recharge.getId(),Constants.RECHARGE_STATUS_SUCCESS);
                        if(rows < 1){
                            throw new RuntimeException("充值状态1更新失败");
                        }
                        result.setResultCode(ResultCode.DUBBO_RECHARGE_SUCCESS);
                    }else{
                        //更新充值记录的状态
                        rows = rechargeMapper.updateStatus(recharge.getId(),Constants.RECHARGE_STATUS_FAIL);
                        if(rows < 1){
                            throw new RuntimeException("充值状态2更新失败");
                        }
                        result.setResultCode(ResultCode.DUBBO_RECHARGE_FAIL);
                    }
                }else{
                    result.setResultCode(ResultCode.DUBBO_RECHARGE_MONEY_ERR);
                }
            }else{
                result.setResultCode(ResultCode.DUBBO_RECHARGE_OVER);
            }
        }else{
            result.setResultCode(ResultCode.DUBBO_RECHARGE_RECORD_NOON);
        }
        return result;
    }
}
