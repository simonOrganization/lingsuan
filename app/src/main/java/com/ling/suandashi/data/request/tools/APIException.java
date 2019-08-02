package com.ling.suandashi.data.request.tools;



/**
 * 接口访问异常类
 * Created by ZHZEPHI on 2016/4/27.
 */
public class APIException extends HttpException {

    HttpException exception;
    String apiCode;

    public APIException(HttpException exception, String apiCode) {
        super(exception.getCode(), exception.getMessage());
        this.exception = exception;
        this.apiCode = apiCode;
    }

    public HttpException getException() {
        return exception;
    }

    public void setException(HttpException exception) {
        this.exception = exception;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }
}
