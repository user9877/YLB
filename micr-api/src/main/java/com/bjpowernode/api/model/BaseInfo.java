package com.bjpowernode.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ClassName:BaseInfo
 * Package:com.bjpowernode.api.model
 * Date:2022/7/6 16:48
 * Description:
 * Autor:FH
 */

public class BaseInfo implements Serializable {
    private static final long serialVersionUID = -127989536456304559L;
    private Integer registerUsers;
    private BigDecimal sumBidMoney;
    private BigDecimal averageRate;

    public Integer getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(Integer registerUsers) {
        this.registerUsers = registerUsers;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }

    public BigDecimal getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(BigDecimal averageRate) {
        this.averageRate = averageRate;
    }

    public BaseInfo(Integer registerUsers, BigDecimal sumBidMoney, BigDecimal averageRate) {
        this.registerUsers = registerUsers;
        this.sumBidMoney = sumBidMoney;
        this.averageRate = averageRate;
    }

    public BaseInfo() {
    }
}
