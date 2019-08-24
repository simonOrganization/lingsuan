package com.ling.suandashi.data;

import com.ling.suandashi.BuildConfig;

/**
 * @author Imxu
 * @time 2019/6/30 14:58
 * @des ${TODO}
 */
public class UrlLists {


    /**
     * 手机号注册  002
     */
    public static final String API_LOGIN = BuildConfig.API_SERVER_URL + "?ct=user&ac=login";

    /**
     * 短信验证码  003
     */
    public static final String API_GET_CODE = BuildConfig.API_SERVER_URL + "?ct=user&ac=sendSms";
    /**
     * 模块列表  004
     */
    public static final String API_MODELU_LIST = BuildConfig.API_SERVER_URL + "?ct=service&ac=module";
    /**
     * 删除联系人  007
     */
    public static final String API_DELETE_USER = BuildConfig.API_SERVER_URL + "?ct=user&ac=delPerson";
    /**
     * 联系人列表  008
     */
    public static final String API_CONTACT_LIST = BuildConfig.API_SERVER_URL + "?ct=user&ac=person";
    /**
     * 添加编辑联系人  009
     */
    public static final String API_ADD_USER = BuildConfig.API_SERVER_URL + "?ct=user&ac=editPerson";

    /**
     * 首页黄历星座接口  011
     */
    public static final String API_HOME = BuildConfig.API_SERVER_URL + "?ct=service&ac=init";
}
