package com.example.myapplication.data.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Order {

    public enum Status {
        PREPARING,   // 制作中
        COMPLETED    // 已完成
    }

    private String orderId;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private String orderTime;
    private Status status;
    private String completeTime;

    public Order(List<CartItem> cartItems) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.orderItems = new ArrayList<>();
        this.status = Status.PREPARING;
        this.orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        // 把 CartItem 转成 OrderItem
        double total = 0;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                    cartItem.getMenuItem().getId(),       // menuItemId
                    cartItem.getMenuItem().getName(),     // name
                    cartItem.getMenuItem().getPrice(),    // price
                    cartItem.getQuantity(),               // quantity
                    cartItem.getRemark()                  // remark
            );
            orderItems.add(orderItem);
            total += orderItem.getSubtotal();
        }
        this.totalPrice = total;
    }

    public String getOrderId() { return orderId; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public double getTotalPrice() { return totalPrice; }
    public String getOrderTime() { return orderTime; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) {
        this.status = status;
        // 状态改为已完成时记录完成时间
        if (status == Status.COMPLETED) {
            this.completeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    .format(new Date());
        }
    }
    public String getCompleteTime() {
        return completeTime != null ? completeTime : "未完成";
    }

    public String getStatusText() {
        return status == Status.PREPARING ? "Preparing" : "Competed";
    }
}