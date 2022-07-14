package com.bjpowernode.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ClassName:Order
 * Package:com.bjpowernode.domain
 * Date:2022/7/5 9:55
 * Description:
 * Autor:FH
 */
@ApiModel(value = "订单实体类",description = "查询订单返回的数据格式")
public class Order {
    @ApiModelProperty(value = "订单主键")
    private Integer oid;
    @ApiModelProperty(value = "订单名称")
    private String name;

    public Order() {
    }

    public Order(Integer oid, String name) {
        this.oid = oid;
        this.name = name;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", name='" + name + '\'' +
                '}';
    }
}
