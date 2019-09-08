package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

/**
 * @author Imxu
 * @time 2019/9/2 14:46
 * @des ${TODO}
 */
public class XingZuoBean {

    public String name;
    public XingZuo day;
    public XingZuo tomorrow;
    public XingZuo week;
    public XingZuo month;
    public XingZuo year;

    public XingZuoBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr,this.getClass());
    }

    public class XingZuo {
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

        public String health_txt;
        public String lucky_day;
        public String week_notice;
        public String xrxz;
        public String yfxz;
        public String work_index;
        public String money_index;
        public String oneword;
        public String general_index;
        public String love_index;

        public int work_star;//工作指数
        public int money_star;//财富指数
        public int love_star;//爱情指数
        public int summary_star;//综合指数
        public int lucky_num;//幸运数字

    }
}
