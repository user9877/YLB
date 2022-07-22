package com.bjpowernode.web.controller;

import com.bjpowernode.api.domain.Recharge;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.view.RechargeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:RechargeController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/22 17:51
 * Description:
 * Autor:FH
 */
@Api(tags = "充值服务")
@RestController
public class RechargeController extends BaseController {

    @ApiOperation(value = "查询用户充值记录")
    @GetMapping("/v1/user/recharge")
    public CommonResult queryRechargeByUid(@RequestHeader("uid") Integer uid,
                                           @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
        CommonResult cr = CommonResult.Fail();
        if (AppUtil.checkuserId(uid)) {
            List<Recharge> rechargeList = rechargeService.queryRechargeByUid(uid, pageNo, pageSize);
            List<RechargeView> viewList = new ArrayList<>();
            rechargeList.forEach(recharge -> {
                viewList.add(new RechargeView(recharge));
            });
                cr = CommonResult.OK();
                cr.setData(viewList);
        }
        return cr;
    }
}
