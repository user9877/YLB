package com.bjpowernode.web.controller;

import com.bjpowernode.api.domain.Product;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
