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

    public String ai;//链接跳转
    public String aiImg;//链接图片
    public HuangLi huangli;
    public XingZuo xingzuo;
    public List<HomeModule> module;

    public HomePageBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr, this.getClass());
    }


    public class HomeModule{
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
    public class XingZuo{
        public String name;//星座名称
        public Day day;//当日运势
    }
    public class Day {
        public String love_txt;//爱情运势
        public String work_txt;//工作运势
        public String lucky_color;//吉色
        public String lucky_time;//吉时
        public String lucky_direction;//吉利方位
        public String time;//时间
        public String money_txt;//财富运势
        public String general_txt;//运势简评
        public String grxz;//贵人星座
        public String day_notice;//今日提醒

        public int work_star;//工作指数
        public int money_star;//财富指数
        public int love_star;//爱情指数
        public int summary_star;//综合指数
        public int lucky_num;//幸运数字

    }

    public class HuangLi{
        public String Yi;//易
        public String Ji;//忌
        public String GregorianDateTime;//日期
        public String LMonth;//农历 月
        public String LDay;//农历 日
    }
}
