package com.example.myapplication.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.menu.MenuRepository;
import com.example.myapplication.data.model.Category;
import com.example.myapplication.data.model.MenuItem;

import java.util.List;

public class MenuViewModel extends ViewModel {
    private MenuRepository menuRepository;
    private MutableLiveData<List<MenuItem>> menuItems = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private MutableLiveData<String> selectedCategory = new MutableLiveData<>();

    public MenuViewModel(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
        loadCategories();
    }
    public LiveData<List<MenuItem>> getMenuItems() {
        return menuItems;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<String> getSelectedCategory() {
        return selectedCategory;
    }
    private void loadCategories() {
        List<Category> categoryList = menuRepository.getCategories();
        if (!categoryList.isEmpty()) {
            categoryList.get(0).setSelected(true);
            selectedCategory.setValue(categoryList.get(0).getName());
            loadMenuItemsByCategory(categoryList.get(0).getName());
        }
        categories.setValue(categoryList);
    }
    public void selectCategory(String categoryName) {
        List<Category> categoryList = categories.getValue();
        if (categoryList != null) {
            for (Category category : categoryList) {
                category.setSelected(category.getName().equals(categoryName));
            }
            categories.setValue(categoryList);
        }
        selectedCategory.setValue(categoryName);
        loadMenuItemsByCategory(categoryName);
    }
    private void loadMenuItemsByCategory(String categoryName) {
        List<MenuItem> items = menuRepository.getMenuItemByCategory(categoryName);
        menuItems.setValue(items);
    }
}
