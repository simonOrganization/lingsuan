package com.ling.suandashi.data.entity;


import com.google.gson.Gson;

/**
 * @author Imxu
 * @time 2019/8/15 11:48
 * @des ${TODO}
 */
public class DreamDetailBean {
    public int id;//文章id
    public String title;//文章标题
    public String type;//文章类型
    public String content;//文章内容

    public DreamDetailBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr,this.getClass());
    }
}
