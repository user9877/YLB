package com.bjpowernode.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ClassName:Student
 * Package:com.bjpowernode.domain
 * Date:2022/7/5 9:01
 * Description:
 * Autor:FH
 */
@ApiModel(value = "学生实体类",description = "查询学生返回的数据格式")
public class Student {

    @ApiModelProperty(value = "学生姓名")
    private String name;
    @ApiModelProperty(name = "id",value = "学生主键值")
    private Integer id;
    @ApiModelProperty(value = "学生年龄")
    private Integer age;

    public Student() {
    }

    public Student(String name, Integer id, Integer age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
