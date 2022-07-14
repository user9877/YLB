package com.bjpowernode.controller;

import com.bjpowernode.domain.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:SwaggerController
 * Package:com.bjpowernode.controller
 * Date:2022/7/5 9:02
 * Description:
 * Autor:FH
 */

/*
@Api:描述类的功能
 */
@Api(tags = "学习swagger")
@RestController
public class SwaggerController {

    /*
    @ApiOperation:放在方法上，对该方法的功能进行描述
     */
    /*
    @ApiImplicitParam：对方法的参数进行说明
     */
    @ApiImplicitParam(name = "id",value = "学生信息的主键")
    @ApiOperation(value = "学生信息查询",notes = "查询学生信息")
    @ApiResponse(code = 200,message = "查询成功")
    @GetMapping("/student/query")
    public Student queryById(Integer id){
        Student student = new Student("莉丝",id,24);
        System.out.println("查询学生：id="+id);
        return student;
    }
}
