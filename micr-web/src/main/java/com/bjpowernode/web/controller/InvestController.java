package com.bjpowernode.web.controller;

import com.bjpowernode.api.model.ProductBidModel;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.util.AppUtil;
import com.bjpowernode.web.resp.CommonResult;
import com.bjpowernode.web.resp.view.ProductBidView;
import com.bjpowernode.web.resp.view.RankView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ClassName:InvestController
 * Package:com.bjpowernode.web.controller
 * Date:2022/7/11 19:46
 * Description:
 * Autor:FH
 */
@CrossOrigin
@Api(tags = "投资业务功能")
@RestController
public class InvestController extends BaseController {
    //投资排行榜
    @ApiOperation(value = "投资排行榜")
    @GetMapping("/v1/invest/rank")
    public CommonResult investMoneyRank() {
        CommonResult cr = CommonResult.OK();
        //从redis获取数据
        Set<ZSetOperations.TypedTuple<String>> zsets = stringRedisTemplate.boundZSetOps(RedisKeyContants.INVEST_MONEY_RANK).reverseRangeWithScores(0, 2);
        List<RankView> rankViewList = new ArrayList<>();
        //遍历集合
        int index = 0;
        for (ZSetOperations.TypedTuple<String> zset : zsets) {
            index = index + 1;
            rankViewList.add(new RankView(index, zset.getValue(), zset.getScore()));
        }
        if (rankViewList.size() == 0) {
            rankViewList.add(new RankView(0, "虚位以待", 0.0));
            rankViewList.add(new RankView(1, "虚位以待", 0.0));
            rankViewList.add(new RankView(2, "虚位以待", 0.0));
        }
        cr.setData(rankViewList);
        return cr;
    }

    //查询用户投资记录
    @ApiOperation(value = "用户投资记录")
    @GetMapping("/v1/user/invest")
    public CommonResult queryByUid(@RequestHeader("uid") Integer uid,
                                   @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
        CommonResult cr = CommonResult.Fail();
        if(AppUtil.checkuserId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            List<ProductBidModel> bidList = investService.queryBidByUserId(uid, pageNo, pageSize);
            List<ProductBidView> viewList = new ArrayList<>();
            bidList.forEach(bid -> {
                viewList.add(new ProductBidView(bid));
            });
            cr = CommonResult.OK();
            cr.setData(viewList);
        }
        return cr;
    }
}
