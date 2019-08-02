package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * @author Imxu
 * @time 2019/7/27 10:03
 * @des ${TODO}
 */
public class HomePageBean implements Serializable {

    public String ai;//链接
    public HuangLi huangli;
    public XingZuo xingzuo;
    public List<HomeModule> module;

    public HomePageBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr, this.getClass());
    }


    class HomeModule{
        public String modelName;
        public int id;
        public String modelPic;
        public int modelPhoto;
        public String modelUrl;

        public String modelCat;
        public String modelStyle;
        public String modelVar;
        public String modelThird;
    }
    class XingZuo{
        public String name;//星座名称
        public Day day;//当日运势
    }
    class Day {
        public String love_txt;
        public String work_txt;
        public String lucky_color;
        public String lucky_time;
        public String lucky_direction;
        public String time;
        public String money_txt;
        public String general_txt;
        public String grxz;
        public String day_notice;

        public int work_star;
        public int money_star;
        public int love_star;
        public int summary_star;
        public int lucky_num;

    }

    class HuangLi{
        public String Yi;//易
        public String Ji;//忌
    }
}
