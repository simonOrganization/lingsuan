package com.ling.suandashi.data.request;


import android.text.TextUtils;

import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;

import org.json.JSONException;

/**
 * 添加和编辑联系人
 */
public class AddAndEditUserRequest extends BaseRequestData {

    public AddAndEditUserRequest(String user_id, String name,String brithday,String hour,boolean male,String id) {
        super();
        params.put("iemi", user_id);
        params.put("name", name);
        params.put("brithday", brithday);
        params.put("hour", hour);
        if(male){
            params.put("gender", "1");
        }else {
            params.put("gender", "0");
        }
        if(!TextUtils.isEmpty(id)){
            params.put("id", id);
        }
    }

    @Override
    public String getUrl() {
        return UrlLists.API_ADD_USER;
    }

    @Override
    public User getResponse(RequestResult result){
        return new User().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30009";
    }
}
