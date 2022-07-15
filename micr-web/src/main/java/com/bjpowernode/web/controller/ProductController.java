package com.bjpowernode.web.controller;

import com.bjpowernode.api.domain.Product;
import com.bjpowernode.api.model.ProductBidModel;
import com.bjpowernode.api.model.UserAccountModel;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.PageInfo;
import com.bjpowernode.web.resp.view.ProductBidView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ProductController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/11 21:13
 * Description:
 * Autor:FH
 */
@RestController
@CrossOrigin
@Api(tags = "产品功能")
public class ProductController extends BaseController{
    private Integer productType;
    private Integer pageNo;
    private Integer pageSize;
    @ApiOperation(value = "分页查询产品")
    @GetMapping("/v1/product/list")
    public CommonResult queryPageProductByType(@RequestParam("productType") Integer productType,        @RequestParam(value = "pageNo",required = false) Integer pageNo,
          @RequestParam(value = "pageSize",required = false,defaultValue = "9") Integer pageSize){
        CommonResult cr = CommonResult.Fail();
        if(AppUtil.checkProductType(productType)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            Integer records = productService.staticsRecordsByType(productType);
            if(records != null && records.intValue() > 0){
                List<Product> productList = productService.queryProductByType(
                        productType, pageNo, pageSize);
                cr = CommonResult.OK();
                Map<String,Object> resp = new HashMap<>();
                resp.put("page",new PageInfo(pageNo,pageSize,records));
                resp.put("productList",productList);
                cr.setData(resp);
            }
        }
        return cr;
    }

    @ApiOperation(value = "单个产品详情")
    @GetMapping("v1/product/detail")
    public CommonResult queryProductDetail(@RequestParam("productId") Integer productId,
                    @RequestHeader(value = "uid",required = false) Integer uid){
        CommonResult cr = CommonResult.Fail();
        if(AppUtil.checkProductId(productId)){
            //1.查询产品信息
            Product product = productService.queryProductById(productId);
            if(product != null){
                Map<String,Object> data = new HashMap<>();
                data.put("product",product);
                //2.查询购买此产品的投资记录
                List<ProductBidModel> bidModelList = investService.queryBidByProductId(productId,1,5);
                List<ProductBidView> viewList = new ArrayList<>();
                bidModelList.forEach( bid -> {
                    viewList.add(new ProductBidView(bid));
                });
                data.put("bidList",viewList);
                //3.查询用户
                BigDecimal accountMoney = new BigDecimal("-1");
                if(AppUtil.checkuserId(uid)){
                    UserAccountModel userAccountModel = userService.queryAllInfoByUid(uid);
                    if(userAccountModel != null){
                        accountMoney = userAccountModel.getAvailableMoney();
                    }
                }
                data.put("accountMoney",accountMoney);
                cr = CommonResult.OK();
                cr.setData(data);
            }
        }
        return cr;
    }
}
