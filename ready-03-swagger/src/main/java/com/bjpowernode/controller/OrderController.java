package com.bjpowernode.controller;

import com.bjpowernode.domain.Order;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:OrderController
 * Package:com.bjpowernode.controller
 * Date:2022/7/5 9:56
 * Description:
 * Autor:FH
 */

/*
@Api:描述类的功能
 */
@Api(tags = "订单管理功能")
@RestController
public class OrderController {

    /*
    @ApiOperation:放在方法上，对该方法的功能进行描述
     */
    /*
    @ApiImplicitParam：对方法的参数进行说明
     */
    /*
    @ApiResponse：对查询结果进行说明
     */
    @ApiOperation(value = "订单信息查询",notes = "查询订单信息")
    @ApiImplicitParam(name = "oid",value = "订单信息的主键")
    @ApiResponse(code = 200,message = "查询成功")
    @GetMapping("/order/query")
    public Order queryOrder(Integer oid){
        Order order = new Order(101,"手机");
        return order;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "oid", value = "订单信息主键"),
            @ApiImplicitParam(name = "name", value = "订单的名称")
    })
    @ApiResponse(code = 200,message = "查询成功")
    @ApiOperation(value = "订单信息查询",notes = "查询订单信息")
    @GetMapping("/order/query1")
    public Order queryOrder1(Integer oid,String name){
        Order order = new Order(101,"手机");
        return order;
    }
}
