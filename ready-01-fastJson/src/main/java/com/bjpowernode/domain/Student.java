package com.bjpowernode.domain;

/**
 * ClassName:Student
 * Package:com.bjpowernode.domain
 * Date:2022/7/4 10:53
 * Description:
 * Autor:FH
 */

public class Student {
    private String name;
    private Integer id;
    private Integer age;

    public Student() {
    }

    public Student(String name, Integer id, Integer age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}
