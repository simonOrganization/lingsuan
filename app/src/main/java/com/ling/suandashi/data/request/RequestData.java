package com.ling.suandashi.data.request;


import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.net.RetrofitTools;

import org.json.JSONException;

import java.util.Map;


public interface RequestData {

    /**
     * 获取请求地址
     */
    String getUrl();

    /**
     * 获取请求参数
     */
    Map<String, Object> getParams();

    /**
     * 获取body
     */
    String getBody();

    /**
     * 获取请求网络方法
     */
    RetrofitTools.RequestType getRequestMethod();

    /**
     * 获取解析返回值对象
     */
    Object getResponse(RequestResult result) throws JSONException;

    /**
     * 获取请求API编码
     *
     * @return
     */
    String getApiCode();

}
