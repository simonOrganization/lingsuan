package com.ling.suandashi.data.request;

import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.net.RetrofitTools;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ZHZEPHI on 2015/10/2.
 */
public abstract class BaseRequestData<T> implements RequestData {

    Map<String, Object> params;
    String json;

    public BaseRequestData() {
        params = new HashMap<>();
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String getBody() {
        return json;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public Object getResponse(RequestResult result) throws JSONException {
        return result.getData();
    }

    @Override
    public RetrofitTools.RequestType getRequestMethod() {
        return RetrofitTools.RequestType.POST;
    }
}
