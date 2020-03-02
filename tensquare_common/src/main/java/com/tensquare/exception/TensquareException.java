package com.tensquare.exception;

/**
 * 自定义异常
 * 作用：终止已知不符合业务逻辑的继续执行
 */
public class TensquareException extends RuntimeException {
    public TensquareException(String message){
        super(message);
    }
}
