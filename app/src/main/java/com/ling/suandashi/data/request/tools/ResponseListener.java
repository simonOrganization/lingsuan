package com.ling.suandashi.data.request.tools;

import com.ling.suandashi.data.request.RequestData;

public interface ResponseListener<T> {
    /**
     * 正常业务处理
     */
    void onResponse(T obj);

    /**
     * 异常业务处理
     */
    void onFailure(RequestData params, RequestResult result);

    /**
     * 网络中断
     */
    void onNetworkError(APIException error);
}
