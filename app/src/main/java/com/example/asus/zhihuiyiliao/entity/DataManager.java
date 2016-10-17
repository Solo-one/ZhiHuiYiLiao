package com.example.asus.zhihuiyiliao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/8/25.
 */
public class DataManager {

    public  static List<NewsType> initType(){
        List<NewsType> newsTypes = new ArrayList<>();
        newsTypes.add(new NewsType(2,"医疗新闻","http://apis.baidu.com/tngou/info/list"));

        newsTypes.add(new NewsType(12,"医疗护理","http://apis.baidu.com/tngou/lore/list"));
        newsTypes.add(new NewsType(3,"健康饮食","http://apis.baidu.com/tngou/lore/list"));
        newsTypes.add(new NewsType(8,"育儿宝典","http://apis.baidu.com/tngou/lore/list"));
        newsTypes.add(new NewsType(11,"减肥瘦身","http://apis.baidu.com/tngou/lore/list"));
        newsTypes.add(new NewsType(5,"女性保养","http://apis.baidu.com/tngou/lore/list"));
        newsTypes.add(new NewsType(6,"孕婴手册","http://apis.baidu.com/tngou/lore/list"));

        newsTypes.add(new NewsType(5,"食品新闻","http://apis.baidu.com/tngou/info/list"));
        newsTypes.add(new NewsType(4,"药品新闻","http://apis.baidu.com/tngou/info/list"));
//        newsTypes.add(new NewsType(3,"生活贴士"));
        newsTypes.add(new NewsType(1,"企业要闻","http://apis.baidu.com/tngou/info/list"));
        newsTypes.add(new NewsType(6,"社会热点","http://apis.baidu.com/tngou/info/list"));
//        newsTypes.add(new NewsType(7,"疾病快讯","http://apis.baidu.com/tngou/info/list"));
        newsTypes.add(new NewsType(4,"男性健康","http://apis.baidu.com/tngou/lore/list"));


        return  newsTypes;
    }

}
