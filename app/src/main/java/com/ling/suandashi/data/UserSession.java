package com.ling.suandashi.data;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 存储用户相关信息
 * Created by ZHZEPHI on 2015/12/20 18:02.
 */
public class UserSession {

    private static final String SHAR_RES_FILE = "user";
    private static UserSession resPrefer;
    private SharedPreferences refPerences;

    private UserSession(Context context) {
        refPerences = context.getSharedPreferences(SHAR_RES_FILE, Context.MODE_PRIVATE);
    }

    public static UserSession getInstances(Context context) {
        if (resPrefer == null) {
            resPrefer = new UserSession(context);
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
    public void saveUserUsuallyInfo(String user) {
//        if (!TextUtils.isEmpty(user.getToken())) {
//            saveValue("token", user.getToken());
//        }
    }
}
