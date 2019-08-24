package com.ling.suandashi.data.request;


import android.text.TextUtils;

import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.HomePageBean;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 首页
 */
public class HomePageRequest extends BaseRequestData {

    public HomePageRequest(String brithday) {
        super();
        if(!TextUtils.isEmpty(brithday)){
            params.put("brithday", brithday);
        }
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
