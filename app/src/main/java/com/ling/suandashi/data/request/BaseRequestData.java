package com.ling.suandashi.data.request;

import com.ling.suandashi.BuildConfig;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.net.RetrofitTools;
import com.ling.suandashi.tools.CommonUtils;

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
        params.put("app_version", BuildConfig.VERSION_NAME);
        params.put("app_bundleid", BuildConfig.APPLICATION_ID);
        params.put("app_name", "灵算大师");
        params.put("iemi", CommonUtils.getPhoneIMEI());
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
