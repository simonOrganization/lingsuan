package com.ling.suandashi.data.request;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.ArticleBean;
import com.ling.suandashi.data.request.tools.RequestResult;

import java.util.List;

/**
 * 解梦搜索
 */
public class DreamSearchRequest extends BaseRequestData {

    public DreamSearchRequest(String key) {
        super();
        params.put("keyword",key);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_DREAM_SEARCH;
    }

    @Override
    public List<ArticleBean> getResponse(RequestResult result){
        return new Gson().fromJson(result.getData(),new TypeToken<List<ArticleBean>>(){}.getType());
    }

    @Override
    public String getApiCode() {
        return "30013";
    }
}
