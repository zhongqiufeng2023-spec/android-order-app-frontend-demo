package com.example.myapplication.data.menu;

import com.example.myapplication.data.model.Category;
import com.example.myapplication.data.model.MenuItem;
import java.util.Arrays;
import java.util.List;

public class MenuDataSource {

    // 假数据，后面换成 Retrofit 网络请求
    public List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem("1", "红烧肉", "热菜", 38.0, "", "经典家常菜"),
                new MenuItem("2", "鱼香肉丝", "热菜", 28.0, "", "川菜经典"),
                new MenuItem("3", "宫保鸡丁", "热菜", 32.0, "", "微辣鲜香"),
                new MenuItem("4", "凉拌黄瓜", "冷菜", 12.0, "", "清爽开胃"),
                new MenuItem("5", "皮蛋豆腐", "冷菜", 15.0, "", "嫩滑爽口"),
                new MenuItem("6", "可乐", "饮料", 8.0, "", "冰镇可口"),
                new MenuItem("7", "橙汁", "饮料", 12.0, "", "鲜榨橙汁"),
                new MenuItem("8", "饺子", "主食", 12.0, "", "黑猪肉"),
                new MenuItem("9", "面条", "主食", 12.0, "", "炸酱面"),
                new MenuItem("10", "馄饨", "主食", 12.0, "", "黑猪肉"),
                new MenuItem("11", "包子", "主食", 312.0, "", "黑猪肉"),
                new MenuItem("12", "米饭", "主食", 3.0, "", "东北大米")


        );
    }

    public List<Category> getCategories() {
        return Arrays.asList(
                new Category("1", "热菜"),
                new Category("2", "冷菜"),
                new Category("3", "饮料"),
                new Category("4", "主食")
        );
    }
}