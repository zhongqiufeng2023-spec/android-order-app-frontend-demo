package com.example.myapplication.data.model;

public class CartItem {
    private MenuItem menuItem;
    private int quantity;
    private String remark;
    public CartItem(MenuItem menuItem, int quantity, String remark) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.remark = remark;
    }
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public String getRemark() { return remark; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setRemark(String remark) { this.remark = remark; }

    // 计算小计
    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }
}
