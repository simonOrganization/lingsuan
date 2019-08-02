package com.ling.suandashi.data.request;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.HomePageBean;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

import java.util.List;

/**
 * 联系人列表
 */
public class ContactListRequest extends BaseRequestData {

    public ContactListRequest(String iemi) {
        super();
        params.put("iemi", iemi);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_CONTACT_LIST;
    }

    @Override
    public List<User> getResponse(RequestResult result){
        return new Gson().fromJson(result.getData(),new TypeToken<List<User>>(){}.getType());
    }

    @Override
    public String getApiCode() {
        return "30008";
    }
}
