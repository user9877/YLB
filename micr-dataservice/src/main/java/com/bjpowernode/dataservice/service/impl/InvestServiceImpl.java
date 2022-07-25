package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.BidInfo;
import com.bjpowernode.api.domain.FinanceAccount;
import com.bjpowernode.api.domain.Product;
import com.bjpowernode.api.model.ProductBidModel;
import com.bjpowernode.api.result.RPCResult;
import com.bjpowernode.api.service.InvestService;
import com.bjpowernode.common.Constants;
import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.ProductMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:InvestServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/15 17:38
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = InvestService.class,version = "1.0.0")
public class InvestServiceImpl implements InvestService {
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Autowired
    private FinanceAccountMapper accountMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductBidModel> queryBidByProductId(Integer productId, Integer pageNo, Integer pageSize) {
        List<ProductBidModel> bidModelList = new ArrayList<>();
        if(AppUtil.checkProductId(productId)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            bidModelList = bidInfoMapper.selectBidsByProductId(productId,offset,pageSize);
        }
        return bidModelList;
    }
    //查询某个用户的投资记录
    @Override
    public List<ProductBidModel> queryBidByUserId(Integer uid, Integer pageNo, Integer pageSize) {
        List<ProductBidModel> bidList = new ArrayList<>();
        if(AppUtil.checkProductId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            bidList = bidInfoMapper.selectBidsByUserId(uid,offset,pageSize);
        }
        return bidList;
    }

    @Override
    public RPCResult investProduct(Integer uid, Integer productId, BigDecimal investMoney) {
        RPCResult result = new RPCResult();
        if(AppUtil.checkuserId(uid) && AppUtil.checkProductId(productId)
                && AppUtil.checkInvestMoney(investMoney)){
            int rows = 0;
            //1.查询账号资金
            FinanceAccount account = accountMapper.selectByUidForUpdate(uid);
            if(account != null && AppUtil.judgeBigDecimal(account.getAvailableMoney(),investMoney)){
                //2.查询理财产品
                Product product = productMapper.selectProductByIdForUpdate(productId);
                if(product != null && product.getProductStatus() ==
                        Constants.PRODUCT_STATUS_SELLING_0){
                    //3.判断金额
                    if(AppUtil.judgeBigDecimal(account.getAvailableMoney(),investMoney) &&
                    AppUtil.judgeBigDecimal(investMoney,product.getBidMinLimit())&&
                    AppUtil.judgeBigDecimal(product.getBidMaxLimit(),investMoney)){
                        //4.开始购买产品，扣除账号金额
                        rows = accountMapper.updateAvailableMoney(uid,investMoney);
                        if(rows < 1){
                            throw new RuntimeException("投资金额扣除失败");
                        }
                        //5.更新该产品剩余可购买金额
                        rows = productMapper.updateLeftProductMoney(productId,investMoney);
                        if(rows < 1){
                            throw new RuntimeException("该产品剩余可投资金额不足");
                        }
                        //6.创建投资记录
                        BidInfo bidInfo = new BidInfo();
                        bidInfo.setBidMoney(investMoney);
                        bidInfo.setBidStatus(Constants.BID_STATUS_SUCC_1);
                        bidInfo.setBidTime(new Date());
                        bidInfo.setUid(uid);
                        bidInfo.setProdId(productId);
                        bidInfoMapper.insertSelective(bidInfo);
                        //7.判断产品是否满标
                        Product queryProduct = productMapper.selectProductById(productId);
                        if(queryProduct.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0){
                            //满标
                            Product updateProduct = new Product();
                            updateProduct.setId(queryProduct.getId());
                            updateProduct.setProductStatus(Constants.PRODUCT_STATUS_SELLING_1);
                            updateProduct.setProductFullTime(new Date());
                            rows = productMapper.updateByPrimaryKeySelective(updateProduct);
                            if(rows < 1){
                                throw new RuntimeException("投资产品状态更新失败");
                            }
                        }
                        //8.投资成功
                        result.setResultCode(ResultCode.DUBBO_PARAM_SUCCESS);
                    }else{
                        result.setResultCode(ResultCode.DUBBO_INVEST_MONEY_ERR);
                    }
                }else{
                    result.setResultCode(ResultCode.DUBBO_PRODUCT_NULL);
                }

            }else{
                result.setResultCode(ResultCode.DUBBO_ACCOUNT_MONEY);
            }
        }else{
            result.setResultCode(ResultCode.DUBBO_PARAM_ERROR);
        }
        return result;
    }
}
