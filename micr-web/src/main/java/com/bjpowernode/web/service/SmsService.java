package com.bjpowernode.web.service;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.common.RedisKeyContants;
import com.bjpowernode.web.config.JdwxSmsConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:SmsService
 * Package:com.bjpowernode.web.service
 * Date:2022/7/16 16:28
 * Description:
 * Autor:FH
 */
@Service
public class SmsService {
    @Autowired
    private JdwxSmsConfig jdwxSmsConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //处理下发注册验证码短信
    public boolean sendSmsCodeRegister(String phone){
        boolean handleSms = false;
        //注册时短信的内容
        String random = RandomStringUtils.randomNumeric(6);
        String content = String.format(jdwxSmsConfig.getRegContent(), random);
        System.out.println("注册的短信验证码"+content);
        if (sendSmsCore(phone,content)) {
            //短信发送成功，保存到redis,时效为3分钟
            String key = RedisKeyContants.SMS_CODE_REGISTER+phone;
            stringRedisTemplate.boundValueOps(RedisKeyContants.SMS_CODE_REGISTER+phone)
                                                .set(random,3, TimeUnit.MINUTES);
            //查看是否存放成功
            if (stringRedisTemplate.hasKey(key)) {
                handleSms = true;
            }
        }
        return handleSms;
    }

    private boolean sendSmsCore(String phone,String content){
        boolean isSend = false;
        CloseableHttpClient client = HttpClients.createDefault();
        String url = jdwxSmsConfig.getUrl()+"?mobile="+phone+"&content"+content+"&appkey="+jdwxSmsConfig.getAppkey();
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                //String str = EntityUtils.toString(response.getEntity());
                String str = "{\n" +//模拟发送
                        "    \"code\": \"10000\",\n" +
                        "    \"charge\": false,\n" +
                        "    \"remain\": 1305,\n" +
                        "    \"msg\": \"查询成功\",\n" +
                        "    \"result\": {\n" +
                        "        \"ReturnStatus\": \"Success\",\n" +
                        "        \"Message\": \"ok\",\n" +
                        "        \"RemainPoint\": 420842,\n" +
                        "        \"TaskID\": 18424321,\n" +
                        "        \"SuccessCounts\": 1\n" +
                        "    }\n" +
                        "}";
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    if("10000".equals(jsonObject.getString("code"))){
                        if ("Success".equalsIgnoreCase(
                                jsonObject.getJSONObject("result").getString("ReturnStatus"))) {
                            isSend=true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            isSend=false;
            throw new RuntimeException(e);
        }finally {
            try {
                client.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return isSend;
    }

    public boolean checkSmsCodeRegister(String phone) {
        String key = RedisKeyContants.SMS_CODE_REGISTER+phone;
        return stringRedisTemplate.hasKey(key);
    }
}
