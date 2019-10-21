package com.ling.suandashi.data.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * @author Imxu
 * @time 2019/9/23 9:48
 * @des
 */
public class OrderBean implements Serializable {
    private static final long serialVersionUID = -6422140018491142913L;

    public List<OrderList> list;

    public OrderBean parse(String jsonStr) {
        return new Gson().fromJson(jsonStr, this.getClass());
    }

    public class OrderList implements Serializable{

        private static final long serialVersionUID = -1887680140582657728L;
        public int id;
        public String title;
        public String img;
        public int sub;
        public int status;
        public String url;
    }
}
