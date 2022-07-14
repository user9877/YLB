package com.bjpowernode.api.service;

import com.bjpowernode.api.model.BaseInfo;
import com.bjpowernode.api.model.MultiTypeProducts;

/**
 * ClassName:YlbBaseSevice
 * Package:com.bjpowernode.api.service
 * Date:2022/7/6 16:43
 * Description:盈利宝平台基本信息
 * Autor:FH
 */

public interface YlbBaseService {
    BaseInfo staticsYlbBaseInfo();

    MultiTypeProducts showMultiTypeProducts();
}
