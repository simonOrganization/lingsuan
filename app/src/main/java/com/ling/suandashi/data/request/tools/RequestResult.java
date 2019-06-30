/*
 * RequestResult.java [V1.0.0]
 * classes:com.hugboga.guide.data.entity.RequestResult
 * ZHZEPHI Create at 2015年1月10日 下午8:54:24
 */
package com.ling.suandashi.data.request.tools;

/**
 *
 */
public class RequestResult {

    private Integer status; // 返回状态
    private String data; // 返回信息
    private String message; // 错误信息

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
