package com.bjpowernode.web.resp.view;

import cn.hutool.core.date.DateUtil;
import com.bjpowernode.api.model.ProductBidModel;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * ClassName:ProductBidView
 * Package:com.bjpowernode.web.resp.view
 * Date:2022/7/15 19:43
 * Description:
 * Autor:FH
 */

public class ProductBidView {
    private Integer bid;
    private String productName;

    private String phone;
    private String bidTime;
    private BigDecimal bidMoney;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductBidView(ProductBidModel model) {
        if (model != null) {
            this.bid = model.getId();
            this.productName = model.getProductName();
            this.bidMoney = model.getBidMoney();
            //对手机号码脱敏
            if (model.getPhone() != null && model.getPhone().length() == 11) {
                this.phone = model.getPhone().substring(0, 3) +
                        "******" + model.getPhone().substring(9, 11);
            }
            //处理日期格式
            if (model.getBidTime() != null) {
                if(StringUtils.isBlank(this.productName)){
                    this.bidTime = DateUtil.format(model.getBidTime(), "yyyy-MM-dd HH:mm:ss");
                }else{
                    this.bidTime = DateUtil.format(model.getBidTime(), "yyyy-MM-dd");
                }
            }
        }
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }
}
