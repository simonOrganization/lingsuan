package com.ling.suandashi.data.entity;


import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author Imxu
 * @time 2019/8/15 11:48
 * @des ${TODO}
 */
public class DreamDetailBean  implements Serializable {
    private static final long serialVersionUID = 2162880166424624886L;
    public int id;//文章id
    public String title;//文章标题
    public String type;//文章类型
    public String content;//文章内容

    public DreamDetailBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr,this.getClass());
    }
}
