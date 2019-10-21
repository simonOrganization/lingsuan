package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author Imxu
 * @time 2019/9/2 14:46
 * @des ${TODO}
 */
public class PersonFortuneBean implements Serializable{

    private static final long serialVersionUID = 2091422778678961908L;
    public XingZuoBean xingzuo;
    public Huangli huangli;
    public year jinnian;


    public PersonFortuneBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr,this.getClass());
    }

    public class Huangli implements Serializable {
        private static final long serialVersionUID = 4349124118073643493L;
        public String xishen;
        public String fushen;
        public String caishen;
        public String suisha;
    }

    public class year implements Serializable{
        private static final long serialVersionUID = 7605555406385848353L;
        public String title;
        public String url;
    }
}
