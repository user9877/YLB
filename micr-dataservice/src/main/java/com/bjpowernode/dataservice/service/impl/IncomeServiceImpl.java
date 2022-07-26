package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.BidInfo;
import com.bjpowernode.api.domain.Income;
import com.bjpowernode.api.domain.Product;
import com.bjpowernode.api.model.ProductIncomeModel;
import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.common.Constants;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.IncomeMapper;
import com.bjpowernode.dataservice.mapper.ProductMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ClassName:IncomeServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/22 18:20
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = IncomeService.class,version = "1.0.0")
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private IncomeMapper incomeMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private FinanceAccountMapper accountMapper;

    //查询用户收益
    @Override
    public List<ProductIncomeModel> queryIncomeByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<ProductIncomeModel> incomeList = new ArrayList<>();
        if(AppUtil.checkProductId(uid)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            int offset = (pageNo -1)*pageSize;
            incomeList = incomeMapper.selectIncomeByUserId(uid,offset,pageSize);
        }
        return incomeList;
    }
    //生成收益计划
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateIncomePlan() {
        //1.查询产品，获取满标产品
        Date date = new Date();
        //开始日期
        Date beginTime = DateUtils.truncate(DateUtils.addDays(date,-1), Calendar.DATE);
        //结束日期
        Date endTime = DateUtils.truncate(date,Calendar.DATE);
        List<Product> productList = productMapper.selectSelledProducts(beginTime,endTime);
        //获取每个满标产品的多个投资记录
        BigDecimal dayRate = null;//日利率
        BigDecimal incomeMoney = null;
        Date incomeDate = null;//到期日
        for(Product product : productList){
            List<BidInfo> bidList = bidInfoMapper.selectByProductId(product.getId());
            //计算每个投资记录的收益
            dayRate = product.getRate().divide(new BigDecimal("360"),10, RoundingMode.HALF_UP).divide(new BigDecimal("100"),10,RoundingMode.HALF_UP);
            for(BidInfo bid : bidList){
                //到期时间，利息
                if(product.getProductType() == Constants.PRODUCT_STATUS_SELLING_0){//新手宝
                    incomeMoney = bid.getBidMoney().
                            multiply(dayRate).multiply(new BigDecimal(product.getCycle()));
                    incomeDate = DateUtils.
                            addDays(product.getProductFullTime(),(1+product.getCycle()));
                }else{//优选和散标
                    incomeMoney = bid.getBidMoney().
                            multiply(dayRate).multiply(new BigDecimal(product.getCycle()*30));
                    incomeDate = DateUtils.
                            addDays(product.getProductFullTime(),(1+product.getCycle()*30));
                }
                //更新到收益记录表
                Income income = new Income();
                income.setIncomeDate(incomeDate);
                income.setIncomeMoney(incomeMoney);
                income.setIncomeStatus(Constants.INCOME_STATUS_NOT_BACK);
                income.setBidMoney(bid.getBidMoney());
                income.setBidId(bid.getId());
                income.setUid(bid.getUid());
                income.setProdId(product.getId());
                incomeMapper.insert(income);
            }
            //更新理财产品状态为2
            Product updateProduct = new Product();
            updateProduct.setId(product.getId());
            updateProduct.setProductStatus(Constants.PRODUCT_STATUS_PLAN_2);
            int rows = productMapper.updateByPrimaryKeySelective(updateProduct);
            if(rows < 1){
                throw new RuntimeException("生成收益计划，更新产品状态为2失败");
            }
        }
    }

    //收益返还
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateIncomeBack() {
        int rows = 0;
        //查询到期的收益记录
        List<Income> incomeList = incomeMapper.selectExpiredIncome();
        for(Income income : incomeList){
            //更新账户资金
            rows = accountMapper.updateAvailableMoneyByIncomeBack(income.getUid(),
                    income.getBidMoney(),income.getIncomeMoney());
            if(rows < 1){
                throw new RuntimeException("收益返还资金更新失败");
            }
            //修改收益表状态
            rows = incomeMapper.updateStatus(income.getId(),Constants.INCOME_STATUS_HAS_BACK);
            if(rows < 1){
                throw new RuntimeException("收益状态更新失败");
            }
        }
    }
}
