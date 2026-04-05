package com.example.myapplication.data.order;

import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.data.model.Order;

import java.util.List;

public class OrderRepository {
    private static volatile OrderRepository instance;
    private OrderDataSource dataSource;
    private OrderRepository(OrderDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public static OrderRepository getInstance()
    {
        if(instance == null)
        {
            synchronized (OrderRepository.class)
            {
                if(instance == null)
                {
                    instance = new OrderRepository(new OrderDataSource());
                }
            }
        }
        return instance;
    }
    public void addOrder(List<CartItem> cartItems) {
        dataSource.addOrder(cartItems);
    }

    public List<Order> getCurrentOrders() {
        return dataSource.getCurrentOrders();
    }


    public List<Order> getHistoryOrders() {
        return dataSource.getHistoryOrders();
    }

    public List<Order> getAllOrders() {
        return dataSource.getAllOrders();
    }
}
