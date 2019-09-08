package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.DreamDetailBean;
import com.ling.suandashi.data.request.tools.RequestResult;


/**
 * 解梦文章详情
 */
public class DreamTitleDetailRequest extends BaseRequestData {

    public DreamTitleDetailRequest(int id, int page) {
        super();
        params.put("id", id+"");
        params.put("page", page+"");
    }

    @Override
    public String getUrl() {
        return UrlLists.API_DREAM_TITLE_DETAIL;
    }

    @Override
    public DreamDetailBean getResponse(RequestResult result){
        return new DreamDetailBean().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30012";
    }
}
