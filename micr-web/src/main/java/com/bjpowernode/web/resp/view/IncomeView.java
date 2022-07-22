package com.bjpowernode.web.resp.view;


import cn.hutool.core.date.DateUtil;
import com.bjpowernode.api.model.ProductIncomeModel;

import java.math.BigDecimal;

/**
 * ClassName:IncomeView
 * Package:com.bjpowernode.web.resp.view
 * Date:2022/7/22 18:46
 * Description:
 * Autor:FH
 */

public class IncomeView {
    private Integer id;
    private String productName;
    private String incomeDate;
    private BigDecimal incomeMoney;

    public IncomeView(ProductIncomeModel productIncomeModel) {
        if(productIncomeModel != null){
            this.id = productIncomeModel.getId();
            this.productName = productIncomeModel.getProductName();
            if(productIncomeModel.getIncomeDate() != null){
                this.incomeDate = DateUtil.format(productIncomeModel.getIncomeDate(),"yyyy-MM-dd");
            }
            this.incomeMoney = productIncomeModel.getIncomeMoney();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }
}
