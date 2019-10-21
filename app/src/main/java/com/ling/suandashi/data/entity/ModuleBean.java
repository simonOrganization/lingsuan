package com.ling.suandashi.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Imxu
 * @time 2019/8/15 11:48
 * @des ${TODO}
 */
public class ModuleBean implements Serializable{
    private static final long serialVersionUID = 5458428291945849797L;
    public int cat_id;
    public CatBean cat_name;
    public String cat_pic;
    public List<Module> modelList;

    public class CatBean implements Serializable {
        private static final long serialVersionUID = -5738854403597914265L;
        public int id;
        public int modelStyle;
        public int modelVer;
        public int modelCat;
        public int modelPhoto;
        public int modelThird;
        public int sort;
        public int type;

        public String modelName;
        public String modelPic;
        public String modelUrl;
        public String inputTime;
    }

    public class Module implements Serializable{
        private static final long serialVersionUID = 490254504885377135L;
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
}
