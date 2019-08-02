package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 手机号注册
 */
public class LoginRequest extends BaseRequestData {

    public LoginRequest(String userphone,String areaCode) {
        super();
        params.put("code", areaCode);
        params.put("phone", userphone);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_LOGIN;
    }

    @Override
    public User getResponse(RequestResult result) {
        return new User().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30002";
    }
}
