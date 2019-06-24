package com.ling.suandashi.base;


/**
 * MVP view 基类
 * Created by IMXU on 2018/4/13.
 */

public interface IBaseView {

    /**
     * 显示通用加载页
     */
    void showLoading();

    /**
     * 隐藏通用加载页
     */
    void hideLoading();

    /**
     * 显示网络异常提醒
     * @param error
     */
//    void showNetErrorToastMsg(APIException error);

    /**
     * 显示业务异常提醒
     */
//    void showLogicExecption(RequestData params, RequestResult result);
}
