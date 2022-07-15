package com.bjpowernode.api.service;

import com.bjpowernode.api.domain.Product;

import java.util.List;

/**
 * ClassName:ProductService
 * Package:com.bjpowernode.api.service
 * Date:2022/7/11 21:07
 * Description:
 * Autor:FH
 */

public interface ProductService {
    //分页查询理财产品
    List<Product> queryProductByType(Integer productType,Integer pageNo,Integer pageSize);
    //某个类型的产品记录总数
    Integer staticsRecordsByType(Integer productType);
    //根据主键查询产品
    Product queryProductById(Integer productId);
}
