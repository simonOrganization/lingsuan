package com.ling.suandashi.data.request.tools;



/**
 * 接口访问异常类
 * Created by ZHZEPHI on 2016/4/27.
 */
public class APIException extends HttpException {

    HttpException exception;
    String errorCode;

    public APIException(HttpException exception, String errorCode) {
        super(exception.getCode(), exception.getMessage());
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public HttpException getException() {
        return exception;
    }

    public void setException(HttpException exception) {
        this.exception = exception;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
