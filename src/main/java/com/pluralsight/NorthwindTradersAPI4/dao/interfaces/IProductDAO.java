package com.pluralsight.NorthwindTradersAPI4.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI4.models.Product;

import java.util.List;

public interface IProductDAO {

    List<Product> getAllProducts();

    Product getProductByID(int productID);

    Product addProduct(Product product);
}
