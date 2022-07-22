package com.bjpowernode.api.model;

import com.bjpowernode.api.domain.Income;

/**
 * ClassName:ProductIncomeModel
 * Package:com.bjpowernode.api.model
 * Date:2022/7/22 18:50
 * Description:
 * Autor:FH
 */

public class ProductIncomeModel extends Income {
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
