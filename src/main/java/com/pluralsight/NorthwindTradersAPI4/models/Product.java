package com.pluralsight.NorthwindTradersAPI4.models;

public class Product {
    private int productID;
    private String productName;
    private int categoryID;
    private double unitPrice;

    public Product(int productID, String productName, int categoryID, double unitPrice) {
        this.productID = productID;
        this.productName = productName;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
