package com.example.myapplication.data.menu;

import com.example.myapplication.data.model.Category;
import com.example.myapplication.data.model.MenuItem;

import java.util.List;

public class MenuRepository {
    private static volatile MenuRepository instance;
    private MenuDataSource dataSource;

    private MenuRepository(MenuDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static MenuRepository getInstance() {
        if (instance == null) {
            synchronized (MenuRepository.class) {
                if (instance == null) {
                    instance = new MenuRepository(new MenuDataSource());
                }
            }
        }
        return instance;
    }

    public List<MenuItem> getMenuItems() {
        return dataSource.getMenuItems();
    }

    public List<Category> getCategories() {
        return dataSource.getCategories();
    }

    public List<MenuItem> getMenuItemByCategory(String categoryName)
    {
        List<MenuItem> allItems = dataSource.getMenuItems();
        List<MenuItem> filtered = new java.util.ArrayList<>();
        for (MenuItem item : allItems) {
            if (item.getCategory().equals(categoryName)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
}
