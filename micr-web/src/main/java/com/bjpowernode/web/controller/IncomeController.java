package com.bjpowernode.web.controller;

import com.bjpowernode.api.model.ProductIncomeModel;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.view.IncomeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:IncomeController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/22 18:19
 * Description:
 * Autor:FH
 */
@RestController
@Api(tags = "收益服务")
public class IncomeController extends BaseController{

    @ApiOperation(value = "查询用户收益")
    @GetMapping("/v1/user/income")
    public CommonResult queryIncomeByUid(@RequestHeader("uid") Integer uid,
                                           @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
        CommonResult cr = CommonResult.Fail();
        if (AppUtil.checkuserId(uid)) {
            List<ProductIncomeModel> incomeList = incomeService.queryIncomeByUid(uid, pageNo, pageSize);
            List<IncomeView> viewList = new ArrayList<>();
            incomeList.forEach(income -> {
                viewList.add(new IncomeView(income));
            });
            cr = CommonResult.OK();
            cr.setData(viewList);
        }
        return cr;
    }

}
