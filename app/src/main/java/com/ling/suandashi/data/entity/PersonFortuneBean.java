package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

/**
 * @author Imxu
 * @time 2019/9/2 14:46
 * @des ${TODO}
 */
public class PersonFortuneBean {

    public XingZuoBean xingzuo;
    public Huangli huangli;
    public year jinnian;


    public PersonFortuneBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr,this.getClass());
    }

    public class Huangli{
        public String xishen;
        public String fushen;
        public String caishen;
        public String suisha;
    }

    public class year{
        public String title;
        public String url;
    }
}
