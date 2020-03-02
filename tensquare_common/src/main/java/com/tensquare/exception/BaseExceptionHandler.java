package com.tensquare.exception;

/**
 * 包名：com.changgou.framework.exception
 * 作者：ChenKai
 * 日期：2019-12-25  15:57
 */

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常类
 */
@ControllerAdvice //全局处理异常
public class BaseExceptionHandler {
    //指定处理什么类型的异常，当前处理所有异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
