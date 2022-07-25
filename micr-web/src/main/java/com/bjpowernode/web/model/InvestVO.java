package com.bjpowernode.web.model;

import java.math.BigDecimal;

/**
 * ClassName:InvestVO
 * Package:com.bjpowernode.web.model
 * Date:2022/7/25 16:28
 * Description:
 * Autor:FH
 */

public class InvestVO {
    private BigDecimal investMoney;
    private Integer productId;

    public InvestVO(BigDecimal investMoney, Integer productId) {
        this.investMoney = investMoney;
        this.productId = productId;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
