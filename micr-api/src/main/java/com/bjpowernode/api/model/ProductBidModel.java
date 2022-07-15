package com.bjpowernode.api.model;

import com.bjpowernode.api.domain.BidInfo;

/**
 * ClassName:ProductBidModel
 * Package:com.bjpowernode.api.model
 * Date:2022/7/15 17:30
 * Description:
 * Autor:FH
 */

public class ProductBidModel extends BidInfo {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProductBidModel() {
    }

    public ProductBidModel(String phone) {
        this.phone = phone;
    }
}
