package com.example.myapplication.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.data.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private MutableLiveData<List<CartItem>> cartItems =
            new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    // 加入购物车
    public void addToCart(MenuItem menuItem, int quantity, String remark) {
        List<CartItem> currentList = getCurrentList();

        // 检查购物车里有没有这道菜
        boolean found = false;
        for (CartItem item : currentList) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                // 已有，数量叠加
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        // 没有，新建 CartItem
        if (!found) {
            currentList.add(new CartItem(menuItem, quantity, remark));
        }

        cartItems.setValue(currentList);
    }

    // 增加某个菜品数量
    public void increaseQuantity(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItems.setValue(cartItems.getValue());           // 触发 LiveData 更新
        cartItem.getMenuItem().setQuantity(cartItem.getMenuItem().getQuantity() + 1);   //同步menu
    }

    // 减少某个菜品数量
    public void decreaseQuantity(CartItem cartItem) {
        List<CartItem> currentList = getCurrentList();
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItems.setValue(currentList);                    // 触发更新
            cartItem.getMenuItem().setQuantity(cartItem.getMenuItem().getQuantity() - 1);
        } else {
            cartItem.getMenuItem().setQuantity(0);
            currentList.remove(cartItem);                      // 移除需要列表
            cartItems.setValue(currentList);                    // 触发更新
        }
    }
    // CartViewModel.java 加这个方法
    public void increaseQuantityByMenuItem(MenuItem menuItem) {
        List<CartItem> currentList = getCurrentList();
        for (CartItem item : currentList) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                break;
            }
        }
        cartItems.setValue(currentList);
    }

    public void decreaseQuantityByMenuItem(MenuItem menuItem) {
        List<CartItem> currentList = getCurrentList();
        for (CartItem item : currentList) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    currentList.remove(item);
                }
                break;
            }
        }
        cartItems.setValue(currentList);
    }
    // 清空购物车
    public void clearCart() {
        List<CartItem> currentList = getCurrentList();
        for (CartItem item : currentList)
        {
            item.getMenuItem().clearall();
        }
        cartItems.setValue(new ArrayList<>());
    }

    // 计算总价
    public double getTotalPrice() {
        double total = 0;
        List<CartItem> currentList = cartItems.getValue();
        if (currentList == null) return 0;
        for (CartItem item : currentList) {
            total += item.getSubtotal();
        }
        return total;
    }


    private List<CartItem> getCurrentList() {
        List<CartItem> currentList = cartItems.getValue();
        return currentList != null ? currentList : new ArrayList<>();
    }
}