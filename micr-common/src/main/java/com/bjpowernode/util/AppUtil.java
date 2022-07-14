package com.bjpowernode.util;

/**
 * ClassName:AppUtil
 * Package:com.bjpowernode.util
 * Date:2022/7/11 21:15
 * Description:
 * Autor:FH
 */

public class AppUtil {
    public static boolean checkProductType(Integer productType){
        boolean flag = false;
        if (productType != null) {
            if(productType > -1 && productType < 3){
                flag = true;
            }
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

}
