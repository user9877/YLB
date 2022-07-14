package com.bjpowernode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:MyTest
 * Package:com.bjpowernode
 * Date:2022/7/4 21:01
 * Description:httpClient是一个基于http协议的网络请求处理库
 * Autor:FH
 */

public class MyTest {
    /*
     *get请求
     */
    @Test
    public void testHttpClientGet()  {
        //1.创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get请求，创建HttpGet对象
        String url = "https://restapi.amap.com/v3/ip?key=0113a13c88697dcea6a445584d535837&ip=60.25.188.64";
        HttpGet get = new HttpGet(url);
        //3.执行请求
        try {
            CloseableHttpResponse response = client.execute(get);
            //4.获取调用结果
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String strResponse = EntityUtils.toString(response.getEntity());
                System.out.println("应答结果："+strResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
                try {
                    if(client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

    }

    /*
     *post请求
     */
    @Test
    public void testHttpClientPost(){
        //1.创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用post请求，创建HttpPost对象
        String url = "https://restapi.amap.com/v3/ip";
        HttpPost post = new HttpPost(url);
        //模拟form
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("key","0113a13c88697dcea6a445584d535837"));
            params.add(new BasicNameValuePair("ip","60.25.188.64"));
            HttpEntity entity = new UrlEncodedFormEntity(params);
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);
            //4.发送请求
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String strResponse = EntityUtils.toString(response.getEntity());
                System.out.println("应答结果："+strResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(client != null) {
                    client.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
