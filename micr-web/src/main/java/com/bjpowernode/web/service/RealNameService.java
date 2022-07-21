package com.bjpowernode.web.service;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.api.service.user.UserService;
import com.bjpowernode.web.config.JdwxRealNameConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:RealNameService
 * Package:com.bjpowernode.web.service
 * Date:2022/7/21 15:16
 * Description:
 * Autor:FH
 */
@Service
public class RealNameService {
    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;
    @Autowired
    private JdwxRealNameConfig realNameConfig;
    public boolean handleRealName(Integer uid,String name,String idCard){
        boolean realname = false;
        Map<String,String> param = new HashMap<>();
        param.put("cardNo",idCard);
        param.put("realName",name);
        param.put("appkey",realNameConfig.getAppkey());
        try {
            //String response = HttpClientUtils.doGet(realNameConfig.getUrl(), param);
            String response = "{\n" +
                    "    \"code\": \"10000\",\n" +
                    "    \"charge\": false,\n" +
                    "    \"remain\": 1305,\n" +
                    "    \"msg\": \"查询成功\",\n" +
                    "    \"result\": {\n" +
                    "        \"error_code\": 0,\n" +
                    "        \"reason\": \"成功\",\n" +
                    "        \"result\": {\n" +
                    "            \"realname\": \""+name+"\",\n" +
                    "            \"idcard\": \"350721197702134399\",\n" +
                    "            \"isok\": true\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            if(StringUtils.isNotBlank(response)){
                JSONObject jsonObject = JSONObject.parseObject(response);
                if("10000".equals(jsonObject.getString("code"))){
                    realname = jsonObject.getJSONObject("result").getJSONObject("result").getBooleanValue("isok");
                    //更新数据库user信息
                    if(realname){
                        realname = userService.modifyRealName(uid,name,idCard);
                    }
                }
            }
        } catch (Exception e) {
            realname = false;
            e.printStackTrace();
        }
        return realname;
    }
}
