package com.bjpowernode.dataservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.api.domain.Product;
import com.bjpowernode.api.model.BaseInfo;
import com.bjpowernode.api.model.MultiTypeProducts;
import com.bjpowernode.api.service.YlbBaseService;
import com.bjpowernode.common.Constants;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.ProductMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:YlbBaseServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/6 16:53
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = YlbBaseService.class, version = "1.0.0")
public class YlbBaseServiceImpl implements YlbBaseService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /*首页三项数据*/
    @Override
    public  BaseInfo staticsYlbBaseInfo() {
        BaseInfo baseInfo = null;
        //从redis中获取数据
        String key = RedisKeyContants.YLB_BASE_INFO;
        BoundValueOperations<String,String> ops = redisTemplate.boundValueOps(key);
        String baseInfoStr = ops.get();
        try {
            //如果没有，则去数据库中查找
            if(baseInfoStr == null || baseInfoStr.equals("")){
                synchronized (this){//同步代码块
                    baseInfoStr = ops.get();
                    if(baseInfoStr == null || baseInfoStr.equals("")){
                        //查询已注册的用户数
                        Integer registerUsers = userMapper.countRegisterUsers();
                        BigDecimal sumBidMoney = bidInfoMapper.selectSumAllBidMoney();
                        BigDecimal averageRate = productMapper.selectHistoryAverageRate();
                        baseInfo = new BaseInfo(registerUsers, sumBidMoney, averageRate);
                        //转为json格式，存到redis中，失效时间为1小时
                        String json = JSONObject.toJSONString(baseInfo);
                        redisTemplate.boundValueOps(RedisKeyContants.YLB_BASE_INFO).
                                set(json,60, TimeUnit.MINUTES);
                    }else{
                        //如果redis中有数据，则把数据转为BaseInfo类型
                        baseInfo = JSONObject.parseObject(baseInfoStr,BaseInfo.class);
                    }
                }
            }else{
                //如果redis中有数据，则把数据转为BaseInfo类型
                baseInfo = JSONObject.parseObject(baseInfoStr,BaseInfo.class);
            }
        } catch (Exception e) {
            baseInfo = new BaseInfo();
            e.printStackTrace();
        }
        return baseInfo;
    }

    /*首页三款理财产品*/
    @Override
    public MultiTypeProducts showMultiTypeProducts() {
        MultiTypeProducts products = null;
        //从redis中获取数据
        String key = RedisKeyContants.YLB_BASE_PRODUCTS;
        BoundValueOperations<String,String> ops = redisTemplate.boundValueOps(key);
        String productsJson = ops.get();
        try {
            //redis中没有数据
            if(productsJson == null || productsJson.equals("")){
                synchronized (this){//同步代码块
                    productsJson = ops.get();
                    if(productsJson == null || productsJson.equals("")){
                        List<Product> xsbList =
                                productMapper.selectProductsByType(Constants.PRODUCT_TYPE_XSB_0, 0, 1);
                        List<Product> yxList =
                                productMapper.selectProductsByType(Constants.PRODUCT_TYPE_YX_1, 0, 3);
                        List<Product> sbList =
                                productMapper.selectProductsByType(Constants.PRODUCT_TYPE_SB_2, 0, 3);
                        products = new MultiTypeProducts(xsbList,yxList,sbList);
                        //存到redis
                        ops.set(JSONObject.toJSONString(products),60,TimeUnit.MINUTES);
                    }else {//redis中有数据
                        //取出来并转换成json格式
                        products = JSON.parseObject(productsJson,MultiTypeProducts.class);
                    }
                }
            }else{//redis中有数据
                //取出来并转换成json格式
                products = JSON.parseObject(productsJson,MultiTypeProducts.class);
            }
        } catch (Exception e) {
            products = new MultiTypeProducts();
            e.printStackTrace();
        }

        return products;
    }

}
