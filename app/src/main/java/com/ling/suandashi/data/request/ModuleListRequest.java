package com.ling.suandashi.data.request;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.ModuleBean;
import com.ling.suandashi.data.request.tools.RequestResult;

import java.util.List;

/**
 * 模块列表
 */
public class ModuleListRequest extends BaseRequestData {

    public ModuleListRequest() {
        super();
    }

    @Override
    public String getUrl() {
        return UrlLists.API_MODELU_LIST;
    }

    @Override
    public List<ModuleBean> getResponse(RequestResult result) {
        return new Gson().fromJson(result.getData(),new TypeToken<List<ModuleBean>>(){}.getType());
    }

    @Override
    public String getApiCode() {
        return "30004";
    }
}
