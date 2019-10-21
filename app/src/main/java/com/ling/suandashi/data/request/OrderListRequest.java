package com.ling.suandashi.data.request;


import com.ling.suandashi.data.UrlLists;
import com.ling.suandashi.data.entity.OrderBean;
import com.ling.suandashi.data.request.tools.RequestResult;

/**
 * 订单列表
 */
public class OrderListRequest extends BaseRequestData {

    public OrderListRequest(int status) {
        super();
//        params.put("iemi", CommonUtils.getPhoneIMEI());
        params.put("iemi", "000000007449e5af7449e5af00000000");
        params.put("status", status+"");
    }

    @Override
    public String getUrl() {
        return UrlLists.API_ORDER_LIST;
    }

    @Override
    public OrderBean getResponse(RequestResult result) {
        return new OrderBean().parse(result.getData());
    }

    @Override
    public String getApiCode() {
        return "30016";
    }
}
