package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.PersonFortuneBean;
import com.ling.suandashi.data.request.tools.RequestResult;


/**
 * 个人运势
 */
public class PersonYunShiRequest extends BaseRequestData {

    public PersonYunShiRequest(String data) {
        super();
        params.put("brithday",data);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_PERSON_FORTUNE;
    }

    @Override
    public PersonFortuneBean getResponse(RequestResult result){
        return new PersonFortuneBean().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30015";
    }
}
