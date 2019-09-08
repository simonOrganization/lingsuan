package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.DreamDetailBean;
import com.ling.suandashi.data.entity.XingZuoBean;
import com.ling.suandashi.data.request.tools.RequestResult;


/**
 * 星座运势
 */
public class XingZuoRequest extends BaseRequestData {

    public XingZuoRequest(String name) {
        super();
        params.put("xingzuo", name);
    }

    @Override
    public String getUrl() {
        return UrlLists.API_XINGZUO;
    }

    @Override
    public XingZuoBean getResponse(RequestResult result){
        return new XingZuoBean().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30014";
    }
}
