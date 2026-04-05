package com.example.myapplication.data.model;

public class MenuItem {
    private String id;
    private String name;
    private String category;
    private double price;
    private String imageUrl;
    private String description;
    private int quantity; // 购物车数量，默认0

    public MenuItem(String id, String name, String category,
                    double price, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.quantity = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void clearall(){this.quantity = 0;}
}
