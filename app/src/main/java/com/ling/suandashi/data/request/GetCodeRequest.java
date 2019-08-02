package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 手机号注册
 */
public class GetCodeRequest extends BaseRequestData {

    public GetCodeRequest(String userphone) {
        super();
        params.put("phone", userphone);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_GET_CODE;
    }

    @Override
    public User getResponse(RequestResult result) {
        return new User().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30003";
    }
}
