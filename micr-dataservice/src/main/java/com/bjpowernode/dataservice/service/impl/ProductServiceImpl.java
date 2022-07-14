package com.bjpowernode.dataservice.service.impl;

import com.bjpowernode.api.domain.Product;
import com.bjpowernode.api.service.ProductService;
import com.bjpowernode.dataservice.mapper.ProductMapper;
import com.bjpowernode.util.AppUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ProductServiceImpl
 * Package:com.bjpowernode.dataservice.service.impl
 * Date:2022/7/11 21:10
 * Description:
 * Autor:FH
 */
@DubboService(interfaceClass = ProductService.class,version ="1.0.0" )
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    //按类型分页查询
    @Override
    public List<Product> queryProductByType(Integer productType, Integer pageNo, Integer pageSize) {
        List<Product>  productList = new ArrayList<>();
        //检查参数
        if(AppUtil.checkProductType(productType)){
            pageNo = AppUtil.defaultPageNo(pageNo);
            pageSize = AppUtil.defaultPageSize(pageSize);
            //计算offset,使用offset参数
            int offset = (pageNo - 1)*pageSize;
            productList = productMapper.selectProductsByType(productType, offset, pageSize);
        }
        return productList;
    }
    //某个产品的总记录条数
    @Override
    public Integer staticsRecordsByType(Integer productType) {
        Integer records = -1;
        if(AppUtil.checkProductType(productType)){
            records = productMapper.countByType(productType);
        }
        return records;
    }
}
