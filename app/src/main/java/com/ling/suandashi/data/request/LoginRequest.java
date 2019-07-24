package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 登录操作
 */
public class LoginRequest extends BaseRequestData {

    public LoginRequest(String areaCode, String userphone, String password) {
        super();
        params.put("areaCode", areaCode);
        params.put("mobile", userphone);
        params.put("password", password);
    }

    public LoginRequest() {
        super();
    }

    @Override
    public String getUrl() {
        return UrlLists.API_HOME;
    }

    @Override
    public User getResponse(RequestResult result) throws JSONException {
        return new User().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30024";
    }
}
