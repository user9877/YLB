package com.bjpowernode.pay.excep;

import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.web.resp.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName:GlobalExceptionHandler
 * Package:com.bjpowernode.web.excep
 * Date:2022/7/29 17:00
 * Description:给有RestController类增加功能，给类中的每个方法增加异常处理功能
 * Autor:FH
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ArithmeticException.class})
    public CommonResult doArithException(ArithmeticException e){
        CommonResult result = CommonResult.Fail();
        result.setResult(ResultCode.FRONT_EXCEP_ARITH);
        return result;
    }
    @ExceptionHandler(value = {NullPointerException.class})
    public CommonResult doNullPointerException(NullPointerException e){
        CommonResult result = CommonResult.Fail();
        result.setResult(ResultCode.FRONT_EXCEP_NULL);
        return result;
    }
    @ExceptionHandler
    public CommonResult doOther(Exception e){
        CommonResult result = CommonResult.Fail();
        result.setResult(ResultCode.FRONT_EXCEP_OTHER);
        return result;
    }
}
