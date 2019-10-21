package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 用户信息
 */
public class User implements Serializable {

    private static final long serialVersionUID = -9034588307651618103L;
    private int id; //联系人ID
    private String name; //联系人姓名
    private String brithday; //联系人生日
    private String hour; //联系人生辰

    private boolean isSelect = false;//是否被选中

    public User parse(String jsonStr) {
        return new Gson().fromJson(jsonStr, this.getClass());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrithday() {
        return brithday;
    }

    public String getHour() {
        return hour;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
