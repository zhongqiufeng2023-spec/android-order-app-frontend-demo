package com.example.myapplication.data.order;

import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.data.model.MenuItem;
import com.example.myapplication.data.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderDataSource {

    private List<Order> orders = new ArrayList<>();

    // 构造函数里加假数据
    public OrderDataSource() {
        // 造几个假的历史订单
        List<CartItem> fakeCart1 = new ArrayList<>();
        fakeCart1.add(new CartItem(
                new MenuItem("1", "红烧肉", "热菜", 38.0, "", "经典家常菜"), 2, "少盐"));
        fakeCart1.add(new CartItem(
                new MenuItem("6", "可乐", "饮料", 8.0, "", "冰镇可口"), 1, ""));
        Order order1 = new Order(fakeCart1);
        order1.setStatus(Order.Status.COMPLETED);  // 设置为已完成
        orders.add(order1);

        List<CartItem> fakeCart2 = new ArrayList<>();
        fakeCart2.add(new CartItem(
                new MenuItem("2", "鱼香肉丝", "热菜", 28.0, "", "川菜经典"), 1, "少辣"));
        fakeCart2.add(new CartItem(
                new MenuItem("8", "米饭", "主食", 3.0, "", "东北大米"), 2, ""));
        Order order2 = new Order(fakeCart2);
        order2.setStatus(Order.Status.COMPLETED);
        orders.add(order2);

        // 当前订单，制作中
        List<CartItem> fakeCart3 = new ArrayList<>();
        fakeCart3.add(new CartItem(
                new MenuItem("3", "宫保鸡丁", "热菜", 32.0, "", "微辣鲜香"), 1, "不要花生"));
        Order order3 = new Order(fakeCart3);  // 默认状态是制作中
        orders.add(order3);
    }

    // 新增订单
    public void addOrder(List<CartItem> cartItems) {
        Order order = new Order(cartItems);
        orders.add(order);
    }

    public List<Order> getCurrentOrders() {
        List<Order> current = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.PREPARING) {
                current.add(order);
            }
        }
        return current;
    }

    public List<Order> getHistoryOrders() {
        List<Order> history = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.COMPLETED) {
                history.add(order);
            }
        }
        return history;
    }

    // 获取所有订单
    public List<Order> getAllOrders() {
        return orders;
    }
}