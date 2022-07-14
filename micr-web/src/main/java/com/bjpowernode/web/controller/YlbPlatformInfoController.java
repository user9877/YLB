package com.bjpowernode.web.controller;

import com.bjpowernode.api.model.BaseInfo;
import com.bjpowernode.api.model.MultiTypeProducts;
import com.bjpowernode.web.resp.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:YlbPlatformInfoController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/6 17:48
 * Description:
 * Autor:FH
 */
@CrossOrigin
@Api(tags = "平台基本信息服务")
@RestController
public class YlbPlatformInfoController  extends BaseController{
    /*首页三项数据*/
    @ApiOperation(value = "平台基本三项数据", notes = "注册用户数，累计成交金额，利率平均值")
    @GetMapping("v1/base/info")
    public CommonResult showBaseInfo(){
        CommonResult cr = CommonResult.OK();
        BaseInfo baseInfo = ylbBaseService.staticsYlbBaseInfo();
        cr.setData(baseInfo);
        return cr;
    }
    /*首页三款理财产品*/
    @ApiOperation(value = "首页多个理财产品" ,notes ="一个新手宝,三个优选和散标产品" )
    @GetMapping("v1/base/products")
    public CommonResult showMultiTypeProducts(){
        CommonResult cr = CommonResult.OK();
        MultiTypeProducts multiTypeProducts = ylbBaseService.showMultiTypeProducts();
        cr.setData(multiTypeProducts);
        return cr;
    }


}
