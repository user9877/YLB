package com.bjpowernode.web.exception;

import com.bjpowernode.common.enums.ResultCode;
import com.bjpowernode.web.resp.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Package:com.abc.front.exception
 * Description:全局异常处理类
 */
@RestControllerAdvice //使用AOP技术，异常消息通知，后置通知
public class GlobalExceptionHandler {

    /*
     * @ExceptionHandler表示方法处理异常
     *   value：异常类型，即控制器controller中抛出的异常与value中异常类型匹配时，执行该方法
     *          那么异常代码处理交由该方法
     * 方法参数：即异常类型，该异常就是控制器抛出的异常对象，通过该参数可以获取异常信息
     * */


    /**
     * 数字转换异常
     *
     * @param e NumberFormatException异常对象
     * @return Result
     */
    @ExceptionHandler(value = {NumberFormatException.class})
    public CommonResult doNumberFormatException(NumberFormatException e) {
        CommonResult result = CommonResult.Fail();
        result.setResult(ResultCode.FAILURE);
        return result;
    }

    /**
     * 空指针异常
     *
     * @param e NullPointerException异常对象
     * @return
     */
    @ExceptionHandler(value = {NullPointerException.class})
    public CommonResult doNullPointerException(NullPointerException e) {
        CommonResult result = CommonResult.Fail();
        result.setResult(ResultCode.FAILURE);
        return result;
    }

    /**
     * 处理其它异常
     *
     * @param e Exception异常对象
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public CommonResult doOtherException(Exception e) {
        CommonResult result = CommonResult.Fail();
        return result;
    }
}
