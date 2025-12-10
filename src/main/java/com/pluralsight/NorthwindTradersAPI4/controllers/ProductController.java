package com.pluralsight.NorthwindTradersAPI4.controllers;

import com.pluralsight.northwindTradersAPI3.dao.interfaces.IProductDAO;
import com.pluralsight.northwindTradersAPI3.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private IProductDAO productDAO;

    @Autowired
    public ProductController (IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @RequestMapping(path = "/products/{productID}", method = RequestMethod.GET)
    public Product getProductByID(@PathVariable int productID) {
        return productDAO.getProductByID(productID);
    }
}
