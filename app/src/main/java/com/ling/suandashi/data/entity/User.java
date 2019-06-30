package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

/**
 * 用户信息
 */
public class User {

    private String fundAccountId; //资金账户ID

    public User parse(String jsonStr) {
        return new Gson().fromJson(jsonStr, this.getClass());
    }

}
