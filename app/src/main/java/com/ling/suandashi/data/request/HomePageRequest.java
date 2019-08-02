package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.HomePageBean;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 登录操作
 */
public class HomePageRequest extends BaseRequestData {

    public HomePageRequest(String brithday) {
        super();
        params.put("brithday", brithday);
    }

    public HomePageRequest() {
        super();
    }

    @Override
    public String getUrl() {
        return UrlLists.API_HOME;
    }

    @Override
    public HomePageBean getResponse(RequestResult result) {
        return new HomePageBean().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30011";
    }
}
