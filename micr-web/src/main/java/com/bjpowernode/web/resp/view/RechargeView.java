package com.bjpowernode.web.resp.view;

import cn.hutool.core.date.DateUtil;
import com.bjpowernode.api.domain.Recharge;

import java.math.BigDecimal;

/**
 * ClassName:RechargeView
 * Package:com.bjpowernode.web.resp.view
 * Date:2022/7/22 18:04
 * Description:
 * Autor:FH
 */

public class RechargeView {
    private Integer id;
    private BigDecimal rechargeMoney;
    private String rechargeTime;
    private String result;

    public RechargeView(Recharge recharge) {
        this.id = recharge.getId();
        this.rechargeMoney = recharge.getRechargeMoney();
        if(recharge.getRechargeTime() != null){
            this.rechargeTime = DateUtil.format(recharge.getRechargeTime(),"yyyy-MM-dd");
        }
        switch (recharge.getRechargeStatus()){
            case 0:
                this.result = "充值中";
                break;
            case 1:
                this.result = "成功";
                break;
            case 2:
                this.result = "失败";
                break;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
