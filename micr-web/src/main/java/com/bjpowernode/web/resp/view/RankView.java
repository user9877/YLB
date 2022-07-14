package com.bjpowernode.web.resp.view;

import io.swagger.annotations.ApiModel;

/**
 * ClassName:RankView
 * Package:com.bjpowernode.web.resp.view
 * Date:2022/7/11 20:00
 * Description:
 * Autor:FH
 */
@ApiModel(value = "投资排行榜")
public class RankView {
    private String phone;
    private Double money;
    private Integer index;

    public RankView() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public RankView(Integer index,String phone, Double money ) {
        if (phone != null && phone.length()==11){
            this.phone = phone.substring(0,3) + "******" + phone.substring(9);
        }else{
            this.phone = phone;
        }
        this.money = money;
        this.index = index;
    }
}
