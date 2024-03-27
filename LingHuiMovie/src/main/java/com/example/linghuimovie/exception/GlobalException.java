package com.example.linghuimovie.exception;

import com.example.linghuimovie.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/2/26 10:27
 * 这是一个全局异常处理类
 * 全局异常处理类的作用是：当程序出现异常时，捕获异常，并返回一个友好的错误提示
 */
@ControllerAdvice     //这个方法专门用来处理ServiceException类型的异常。
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serveException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());   //在这个方法中，使用异常的code和message属性来构造一个Result对象并返回。
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e) {
        e.printStackTrace();
        return Result.error("500", "系统错误");
    }
}