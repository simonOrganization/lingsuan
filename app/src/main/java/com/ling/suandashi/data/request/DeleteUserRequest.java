package com.ling.suandashi.data.request;



import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.tools.RequestResult;


/**
 * 删除联系人
 */
public class DeleteUserRequest extends BaseRequestData {

    public DeleteUserRequest(String id) {
        super();
        params.put("id", id);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_DELETE_USER;
    }

    @Override
    public User getResponse(RequestResult result){
        return null;
    }

    @Override
    public String getApiCode() {
        return "30007";
    }
}
