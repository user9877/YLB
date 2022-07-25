package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductMapper {


     BigDecimal selectHistoryAverageRate();

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    /*按类型查询理财产品*/
    List<Product> selectProductsByType(@Param("type") Integer type, @Param("offset") Integer offset, @Param("rows") Integer rows);

    Integer countByType(Integer productType);

    Product selectProductById(@Param("productId") Integer productId);

    Product selectProductByIdForUpdate(@Param("productId") Integer productId);

    int updateLeftProductMoney(@Param("productId") Integer productId, @Param("investMoney") BigDecimal investMoney);
}
