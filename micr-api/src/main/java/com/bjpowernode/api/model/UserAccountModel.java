package com.bjpowernode.api.model;

import com.bjpowernode.api.domain.User;

import java.math.BigDecimal;

/**
 * ClassName:UserAccountModel
 * Package:com.bjpowernode.api.model
 * Date:2022/7/15 18:03
 * Description:
 * Autor:FH
 */

public class UserAccountModel extends User {
    private BigDecimal availableMoney;

    public UserAccountModel(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }

    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}
