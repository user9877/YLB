package com.bjpowernode.pay.util;

/**
 * ClassName:RechargeUtil
 * Package:com.bjpowernode.pay.util
 * Date:2022/7/27 16:13
 * Description:
 * Autor:FH
 */
import java.util.Map;
public class RechargeUtil {
    public static String appendParam(String returns, String paramId, String paramValue,Map<String,String> map) {
        if (returns != "") {
            if (paramValue != null && paramValue != "" ) {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {
            if (paramValue != null && paramValue != "" ) {
                returns = paramId + "=" + paramValue;
            }
        }
        if( map != null ){
            map.put(paramId,paramValue);
        }
        return returns;
    }
    public static String appendParam(String returns, String paramId, String paramValue) {
        if (returns != "") {
            if (paramValue != null && paramValue != "" ) {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {
            if (paramValue != null && paramValue != "" ) {
                returns = paramId + "=" + paramValue;
            }
        }
        return returns;
    }
}
