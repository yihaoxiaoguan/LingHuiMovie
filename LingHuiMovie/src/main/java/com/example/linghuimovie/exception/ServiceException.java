package com.example.linghuimovie.exception;

import lombok.Getter;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/2/26 10:32
 */
@Getter
public class ServiceException extends RuntimeException{

    private  final String code;
    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }
    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}