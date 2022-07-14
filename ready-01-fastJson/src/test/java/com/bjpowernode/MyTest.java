package com.bjpowernode;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.domain.Student;
import org.junit.Test;

/**
 * ClassName:MyTest
 * Package:com.bjpowernode
 * Date:2022/7/4 10:55
 * Description:
 * Autor:FH
 */

public class MyTest {
    @Test
    public void test01(){
        Student student = new Student("李四",1001,24);
        String json = JSONObject.toJSONString(student);
        System.out.println("Student序列化的json="+json);

    }
    @Test
    public void test02(){
        String json = "{\"age\":24,\"id\":1002,\"name\":\"张三\"}";
        Student student = JSONObject.parseObject(json, Student.class);
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println("json反序列化Student="+student);
        System.out.println("json反序列化Student="+jsonObject);
    }
}
