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
     * 解梦文章列表  010
     */
    public static final String API_POST_LIST = BuildConfig.API_SERVER_URL + "?ct=service&ac=postList";

    /**
     * 首页黄历星座接口  011
     */
    public static final String API_HOME = BuildConfig.API_SERVER_URL + "?ct=service&ac=init";

    /**
     * 解梦文章详情  012
     */
    public static final String API_DREAM_TITLE_DETAIL = BuildConfig.API_SERVER_URL + "?ct=service&ac=postDetail";

    /**
     * 解梦搜索  013
     */
    public static final String API_DREAM_SEARCH = BuildConfig.API_SERVER_URL + "?ct=service&ac=jiemeng";

    /**
     * 星座运势  014
     */
    public static final String API_XINGZUO = BuildConfig.API_SERVER_URL + "?ct=service&ac=xingzuo";

    /**
     * 个人运势  015
     */
    public static final String API_PERSON_FORTUNE = BuildConfig.API_SERVER_URL + "?ct=service&ac=personFortune";
}
