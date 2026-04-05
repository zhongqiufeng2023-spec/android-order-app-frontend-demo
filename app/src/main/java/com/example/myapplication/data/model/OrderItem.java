package com.example.myapplication.data.model;

public class OrderItem {
    private String menuItemId;
    private String name;
    private double price;
    private int quantity;
    private String remark;

    // 传具体字段，不依赖 CartItem
    public OrderItem(String menuItemId, String name,
                     double price, int quantity, String remark) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.remark = remark;
    }

    public String getMenuItemId() { return menuItemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getRemark() { return remark; }
    public double getSubtotal() { return price * quantity; }
}