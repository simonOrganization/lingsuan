package com.ling.suandashi.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ling.suandashi.LSApplication;
import com.ling.suandashi.data.entity.User;


/**
 * 存储用户相关信息
 * Created by ZHZEPHI on 2015/12/20 18:02.
 */
public class UserSession {

    private static final String SHAR_RES_FILE = "user";

    public static final String USER_ID = "user_id";
    public static final String USER_SUB_ID = "user_sub_id";
    public static final String USER_SUB_NAME = "user_sub_name";
    public static final String USER_SUB_BIRTHDAY = "user_sub_birthday";
    public static final String USER_SUB_HOUR = "user_sub_hour";

    public static final String HTTP_KEY = "G$B#SN39T@18JCZR";
    public static final String HTTP_IV = "jdpwlXI5l3bxn3X6";

    private static UserSession resPrefer;
    private SharedPreferences refPerences;

    private UserSession(Context context) {
        refPerences = context.getSharedPreferences(SHAR_RES_FILE, Context.MODE_PRIVATE);
    }

    public static UserSession getInstances() {
        if (resPrefer == null) {
            resPrefer = new UserSession(LSApplication.GlobalContext);
        }
        return resPrefer;
    }

    public void saveValue(String key, String value) {
        SharedPreferences.Editor editor = refPerences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key, String defVal) {
        return refPerences.getString(key, defVal);
    }

    public int getValue(String key, int defVal) {
        return refPerences.getInt(key, defVal);
    }

    public void saveValue(String key, int value) {
        SharedPreferences.Editor editor = refPerences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public long getValue(String key, long defVal) {
        return refPerences.getLong(key, defVal);
    }

    public void saveValue(String key, long value) {
        SharedPreferences.Editor editor = refPerences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public Boolean getValue(String key, Boolean defVal) {
        return refPerences.getBoolean(key, defVal);
    }

    public void saveValue(String key, Boolean value) {
        SharedPreferences.Editor editor = refPerences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存常用的返回信息
     */
    public void saveUserUsuallyInfo(User user) {
        if (user.getId() > 0) {
            saveValue(USER_SUB_ID, user.getId());
        }
        if (!TextUtils.isEmpty(user.getName())) {
            saveValue(USER_SUB_NAME, user.getName());
        }
        if (!TextUtils.isEmpty(user.getBrithday())) {
            saveValue(USER_SUB_BIRTHDAY, user.getBrithday());
        }
        if (!TextUtils.isEmpty(user.getHour())) {
            saveValue(USER_SUB_HOUR, user.getHour());
        }
    }

    public void deleteUserInfo(){
        saveValue(USER_SUB_ID, 0);
        saveValue(USER_SUB_NAME, "");
        saveValue(USER_SUB_BIRTHDAY, "");
        saveValue(USER_SUB_HOUR, "");
    }
}
