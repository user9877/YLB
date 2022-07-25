package com.bjpowernode.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * ClassName:AppUtil
 * Package:com.bjpowernode.util
 * Date:2022/7/11 21:15
 * Description:
 * Autor:FH
 */

public class AppUtil {
    //判断产品类型
    public static boolean checkProductType(Integer productType){
        boolean flag = false;
        if (productType != null) {
            if(productType > -1 && productType < 3){
                flag = true;
            }
        }
        return flag;
    }
 public static boolean checkInvestMoney(BigDecimal investMoney){
        boolean flag = false;
        if (investMoney != null && investMoney.doubleValue() >= 100 && investMoney.doubleValue() % 100 == 0) {
                flag = true;
        }
        return flag;
    }

    //判断产品id
    public static Boolean checkProductId(Integer productId){
        boolean flag = false;
        if(productId != null && productId > 0){
            flag = true;
        }
        return flag;
    }

    //判断userId
    public static Boolean checkuserId(Integer userId){
        boolean flag = false;
        if(userId != null && userId > 0){
            flag = true;
        }
        return flag;
    }
    //页号
    public static Integer defaultPageNo(Integer pageNo){
        if(pageNo == null || pageNo<1){
            pageNo=1;
        }
        return pageNo;
    }
    //页大小
    public static Integer defaultPageSize(Integer pageSize){
        if(pageSize == null || pageSize<1 || pageSize>100){
            pageSize=9;
        }
        return pageSize;
    }

    //判断手机号格式是否正确
    public static Boolean checkPhone(String phone){
        boolean flag = false;
        if(phone != null){
            flag = Pattern.matches("^1[1-9]\\d{9}$",phone);
        }
        return flag;
    }
    //判断密码格式是否正确
    public static Boolean checkLoginPassword(String loginPassword){
        boolean flag = false;
        if(loginPassword != null && loginPassword.length()==32){
            flag = true;
        }
        return flag;
    }
    //判断两个BigDecimal类型的数的大小
    public static Boolean judgeBigDecimal(BigDecimal n1,BigDecimal n2){
        if(n1 == null && n2 == null){
           throw new RuntimeException("参数不能为空");
        }

        return n1.compareTo(n2) >=0;
    }

}
